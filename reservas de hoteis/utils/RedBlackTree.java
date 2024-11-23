package utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RedBlackTree<T extends Comparable<T>> implements Iterable<T> {
    private class Node {
        T data;
        Node left, right, parent;
        boolean is_red;

        Node(T data) {
            this.data = data;
            this.is_red = true; // Por padrão novos nodos sâo vermelhos
        }
    }

    private Node root;
    private Node TNULL; // Nó sentinela que simula um nó "nulo"

    public RedBlackTree() {
        TNULL = new Node(null);  // TNULL serve como o "nó nulo" para a árvore
        TNULL.is_red = false;    // A cor do nó nulo é sempre preta
        root = TNULL;
    }

    // Implementação do Iterable
    @Override
    public Iterator<T> iterator() {
        List<T> elements = new ArrayList<>();
        in_order_helper(root, elements);
        return elements.iterator();
    }

    // Método auxiliar para coletar elementos in-order
    private void in_order_helper(Node node, List<T> elements) {
        if (node != TNULL) {
            in_order_helper(node.left, elements);
            elements.add(node.data);
            in_order_helper(node.right, elements);
        }
    }

    // Inserir um novo nó
    public void insert(T data) {
        Node node = new Node(data);
        node.left = TNULL;
        node.right = TNULL;
        node.parent = null;

        Node y = null;
        Node x = root;

        while (x != TNULL) {
            y = x;
            if (node.data.compareTo(x.data) < 0) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        node.parent = y;
        if (y == null) {
            root = node;
        } else if (node.data.compareTo(y.data) < 0) {
            y.left = node;
        } else {
            y.right = node;
        }

        if (node.parent == null) {
            node.is_red = false;
            return;
        }

        if (node.parent.parent == null) {
            return;
        }

        fix_insert(node);
    }

    // Corrigir a árvore após a inserção
    private void fix_insert(Node k) {
        Node u;
        while (k.parent.is_red) {
            if (k.parent == k.parent.parent.right) {
                u = k.parent.parent.left;
                if (u.is_red) {
                    u.is_red = false;
                    k.parent.is_red = false;
                    k.parent.parent.is_red = true;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.left) {
                        k = k.parent;
                        right_rotate(k);
                    }
                    k.parent.is_red = false;
                    k.parent.parent.is_red = true;
                    left_rotate(k.parent.parent);
                }
            } else {
                u = k.parent.parent.right;
                if (u.is_red) {
                    u.is_red = false;
                    k.parent.is_red = false;
                    k.parent.parent.is_red = true;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.right) {
                        k = k.parent;
                        left_rotate(k);
                    }
                    k.parent.is_red = false;
                    k.parent.parent.is_red = true;
                    right_rotate(k.parent.parent);
                }
            }
            if (k == root) {
                break;
            }
        }
        root.is_red = false;
    }

    // Rotacionar à esquerda
    private void left_rotate(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != TNULL) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    // Rotacionar à direita
    private void right_rotate(Node x) {
        Node y = x.left;
        x.left = y.right;
        if (y.right != TNULL) {
            y.right.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            root = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;
    }

    // Busca por um nó
    public T search(T key) {
        Node node = search_tree_helper(this.root, key);
        if (node != TNULL) {
            return node.data;
        }
        return null; // Se não encontrar
    }

    private Node search_tree_helper(Node node, T key) {
        if (node == TNULL || key == node.data) {
            return node;
        }

        if (key.compareTo(node.data) < 0) {
            return search_tree_helper(node.left, key);
        }
        return search_tree_helper(node.right, key);
    }

    // In-order traversal para exibir dados
    public void in_order_traversal() {
        in_order_helper(root);
    }

    private void in_order_helper(Node node) {
        if (node != TNULL) {
            in_order_helper(node.left);
            System.out.println(node.data);
            in_order_helper(node.right);
        }
    }

    // Função para remover um nó
    public void remove(T data) {
        Node node = search_tree_helper(this.root, data);

        if (node == TNULL) {
            System.out.println("No encontrado!");
            return; // Caso o nó não exista
        }

        Node y = node;
        Node x;
        boolean originalColor = y.is_red; // Armazena a cor original de y

        if (node.left == TNULL) {
            x = node.right;
            transplant(node, node.right);
        } else if (node.right == TNULL) {
            x = node.left;
            transplant(node, node.left);
        } else {
            y = minimum(node.right); // Encontrar o sucessor em ordem
            originalColor = y.is_red;
            x = y.right;

            if (y.parent == node) {
                x.parent = y;
            } else {
                transplant(y, y.right);
                y.right = node.right;
                y.right.parent = y;
            }

            transplant(node, y);
            y.left = node.left;
            y.left.parent = y;
            y.is_red = node.is_red;
        }

        if (!originalColor) {
            fix_remove(x);
        }
    }

    // Substitui um nó na arvore
    private void transplant(Node u, Node v) {
        if (u.parent == null) {
            root = v;
        } else if (u == u.parent.left) {
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }
        v.parent = u.parent;
    }

    // Encontrar o nó mínimo (sucessor em ordem)
    private Node minimum(Node node) {
        while (node.left != TNULL) {
            node = node.left;
        }
        return node;
    }

    // Ajustar a árvore após a remoção
    private void fix_remove(Node x) {
        while (x != root && !x.is_red) {
            if (x == x.parent.left) {
                Node w = x.parent.right;
                if (w.is_red) {
                    w.is_red = false;
                    x.parent.is_red = true;
                    left_rotate(x.parent);
                    w = x.parent.right;
                }

                if (!w.left.is_red && !w.right.is_red) {
                    w.is_red = true;
                    x = x.parent;
                } else {
                    if (!w.right.is_red) {
                        w.left.is_red = false;
                        w.is_red = true;
                        right_rotate(w);
                        w = x.parent.right;
                    }

                    w.is_red = x.parent.is_red;
                    x.parent.is_red = false;
                    w.right.is_red = false;
                    left_rotate(x.parent);
                    x = root;
                }
            } else {
                Node w = x.parent.left;
                if (w.is_red) {
                    w.is_red = false;
                    x.parent.is_red = true;
                    right_rotate(x.parent);
                    w = x.parent.left;
                }

                if (!w.left.is_red && !w.right.is_red) {
                    w.is_red = true;
                    x = x.parent;
                } else {
                    if (!w.left.is_red) {
                        w.right.is_red = false;
                        w.is_red = true;
                        left_rotate(w);
                        w = x.parent.left;
                    }

                    w.is_red = x.parent.is_red;
                    x.parent.is_red = false;
                    w.left.is_red = false;
                    right_rotate(x.parent);
                    x = root;
                }
            }
        }
        x.is_red = false;
    }
    
}
