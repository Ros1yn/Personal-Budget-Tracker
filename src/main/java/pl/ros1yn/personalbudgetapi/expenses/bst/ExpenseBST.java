package pl.ros1yn.personalbudgetapi.expenses.bst;

import pl.ros1yn.personalbudgetapi.expenses.model.Expenses;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExpenseBST {

    private ExpenseNode root;

    public ExpenseBST() {
        this.root = null;
    }


    public void insert(Expenses e) {
        root = insertRec(root, e);
    }

    private ExpenseNode insertRec(ExpenseNode node, Expenses e) {
        if (node == null) {
            return new ExpenseNode(e);
        }
        LocalDate newDate = e.getExpenseDate();
        LocalDate nodeDate = node.getKeyDate();

        if (newDate.isBefore(nodeDate)) {
            node.setLeft(insertRec(node.getLeft(), e));
        } else {
            node.setRight(insertRec(node.getRight(), e));
        }
        return node;
    }


    public List<Expenses> inorder() {
        List<Expenses> result = new ArrayList<>();
        inorderRec(root, result);
        return result;
    }

    private void inorderRec(ExpenseNode node, List<Expenses> list) {
        if (node != null) {
            inorderRec(node.getLeft(), list);
            list.add(node.getExpense());
            inorderRec(node.getRight(), list);
        }
    }


    public List<Expenses> preorder() {
        List<Expenses> result = new ArrayList<>();
        preorderRec(root, result);
        return result;
    }

    private void preorderRec(ExpenseNode node, List<Expenses> list) {
        if (node != null) {
            list.add(node.getExpense());
            preorderRec(node.getLeft(), list);
            preorderRec(node.getRight(), list);
        }
    }


    public List<Expenses> postorder() {
        List<Expenses> result = new ArrayList<>();
        postorderRec(root, result);
        return result;
    }

    private void postorderRec(ExpenseNode node, List<Expenses> list) {
        if (node != null) {
            postorderRec(node.getLeft(), list);
            postorderRec(node.getRight(), list);
            list.add(node.getExpense());
        }
    }


    public Expenses find(LocalDate date) {
        ExpenseNode node = findRec(root, date);
        return node == null ? null : node.getExpense();
    }

    private ExpenseNode findRec(ExpenseNode node, LocalDate date) {
        if (node == null) {
            return null;
        }
        LocalDate nodeDate = node.getKeyDate();
        if (date.isEqual(nodeDate)) {
            return node;
        } else if (date.isBefore(nodeDate)) {
            return findRec(node.getLeft(), date);
        } else {
            return findRec(node.getRight(), date);
        }
    }


    public void delete(LocalDate date) {
        root = deleteRec(root, date);
    }

    private ExpenseNode deleteRec(ExpenseNode node, LocalDate date) {
        if (node == null) {
            return null;
        }

        LocalDate nodeDate = node.getKeyDate();
        if (date.isBefore(nodeDate)) {
            node.setLeft(deleteRec(node.getLeft(), date));
        } else if (date.isAfter(nodeDate)) {
            node.setRight(deleteRec(node.getRight(), date));
        } else {

            if (node.getLeft() == null) {
                return node.getRight();
            } else if (node.getRight() == null) {
                return node.getLeft();
            }

            ExpenseNode successor = findMin(node.getRight());
            node.setExpense(successor.getExpense());
            node.setRight(deleteRec(node.getRight(), successor.getKeyDate()));
        }

        return node;
    }

    private ExpenseNode findMin(ExpenseNode node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }


    public boolean isEmpty() {
        return root == null;
    }


}
