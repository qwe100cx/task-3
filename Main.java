import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Stack;


class Node {
    String data;
    Node next;

    public Node(String data) {
        this.data = data;
        this.next = null;
    }
}

class LinkedList {
    Node head;
    private Node tail;

    public LinkedList() {
        this.head = null;
        this.tail = null;
    }

    public void addNode(String data) {
        Node newNode = new Node(data);

        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
    }

    public void reverseList() {
        Stack<Node> stack = new Stack<>();
        Node current = head;

        while (current != null) {
            stack.push(current);
            current = current.next;
        }

        head = stack.pop();
        current = head;

        while (!stack.isEmpty()) {
            current.next = stack.pop();
            current = current.next;
        }

        current.next = null;
        tail = current;
    }

    public void clearList() {
        head = null;
        tail = null;
    }

    public void generateRandomList(int size) {
        clearList();
        Random random = new Random();

        for (int i = 0; i < size; i++) {
            addNode(String.valueOf(random.nextInt(100)));
        }
    }

    public void readListFromFile(String filename) {
        clearList();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                addNode(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getListAsString() {
        StringBuilder sb = new StringBuilder();
        Node current = head;

        while (current != null) {
            sb.append(current.data).append(" ");
            current = current.next;
        }

        return sb.toString();
    }
}

public class Main {
    public static void main(String[] args) {
        LinkedList list = new LinkedList();

        JFrame frame = new JFrame("Linked List Reversal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 250);
        frame.setLayout(new FlowLayout());

        JLabel label = new JLabel("Введите список:");
        JTextField textField = new JTextField(20);
        JButton reverseButton = new JButton("перевернуть ");
        JButton clearButton = new JButton("очистить");
        JButton randomButton = new JButton("случайный список");
        JButton fileButton = new JButton("открыть файл");

        JTextArea textArea = new JTextArea(10, 70);
        textArea.setEditable(false);

        reverseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = textField.getText();
                String[] elements = input.split(" ");

                for (String element : elements) {
                    list.addNode(element);
                }

                list.reverseList();

                textArea.setText( list.getListAsString());
            }
        });

        fileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    String filename = fileChooser.getSelectedFile().getAbsolutePath();
                    list.readListFromFile(filename);
                    textArea.setText(list.getListAsString());
                }
            }
        });

        randomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                list.generateRandomList(5);
                textArea.setText(list.getListAsString());
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                list.clearList();
                textArea.setText("пусто");
            }
        });


        frame.add(label);
        frame.add(textField);
        frame.add(reverseButton);
        frame.add(clearButton);
        frame.add(randomButton);
        frame.add(fileButton);
        frame.add(textArea);

        frame.setVisible(true);
    }
}