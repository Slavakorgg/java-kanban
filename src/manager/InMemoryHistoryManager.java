package manager;


import task.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {


    private Node first;
    private Node last;
    private HashMap<Integer, Node> history = new HashMap<>();


    public Node getFirst() {
        return first;
    }

    public Node getLast() {
        return last;
    }

    public void setFirst(Node first) {
        this.first = first;
    }

    public void setLast(Node last) {
        this.last = last;
    }

    public void setHistory(HashMap<Integer, Node> history) {
        this.history = history;
    }

    @Override
    public void add(Task task) {
        Node node = new Node();
        node.setValue(task);

        if (getLast() != null) {
            if (getLast().getValue() == node.getValue()) {
                return;
            }
        }

        if (history.containsKey(task.getId())) {
            remove(task.getId());
            linkLast(node);
        } else {
            linkLast(node);

        }
        history.put(task.getId(), node);


    }

    @Override
    public void remove(int id) {
        Node node = history.get(id);
        removeNode(node);

        history.remove(id, history.get(id));


    }

    public void removeNode(Node node) {
        if (getFirst() == node) {
            setFirst(getFirst().getNext());
            node.setNext(null);

        } else if (getLast() == node ) {
            if (node.getPrevious() == getFirst()){
                getLast().setPrevious(null);
                getFirst().setNext(null);
                setLast(getFirst());
            }
            else  {
                Node newLastNode = node.getPrevious();
                remove(node.getPrevious().getValue().getId());
                setLast(newLastNode);
                node.setNext(null);
                node.setPrevious(null);
            }






        } else {
            node.getPrevious().setNext(node.getNext());
            node.getNext().setPrevious(node.getPrevious());
            node.setNext(null);
            node.setPrevious(null);
        }


    }

    @Override
    public List<Task> getTasks() {
        ArrayList<Task> result = new ArrayList<>();
        if (!history.isEmpty()){
            Node currentNode = getFirst();
            while (currentNode.getNext() != null) {
                result.add(currentNode.getValue());
                currentNode = currentNode.getNext();
            }
            result.add(getLast().getValue());
        }
        return result;
    }

    public void linkLast(Node node){

        if (getLast()!=null){
            getLast().setNext(node);
            node.setPrevious(getLast());
        }
        setLast(node);
        if (history.isEmpty()) setFirst(node);



    }

    static class Node{
        Node previous;
        Node next;
        Task value;


        public void setPrevious(Node previous) {
            this.previous = previous;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public void setValue(Task value) {
            this.value = value;
        }

        public Node getPrevious() {
            return previous;
        }

        public Node getNext() {
            return next;
        }

        public Task getValue() {
            return value;
        }
    }






}
