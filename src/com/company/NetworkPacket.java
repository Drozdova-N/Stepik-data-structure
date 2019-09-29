package com.company;

import com.sun.org.apache.bcel.internal.generic.ARETURN;

import java.util.*;

public class NetworkPacket {

    private int maxSizeBuffer;
    private int currentSize = 0;
    private Node headNode;
    private Node tailNode;

    public NetworkPacket(int maxSizeBuffer) {
        this.maxSizeBuffer = maxSizeBuffer;
    }

    public boolean addPacket(Packet packet) {
        if (currentSize < maxSizeBuffer) {
            if (headNode == null) {
                this.headNode = new Node(packet);
                this.tailNode = this.headNode;

            } else {
                Node node = new Node(packet);
                tailNode.setNext(node);
                tailNode = node;

            }
            currentSize++;
            return true;

        } else
            return false;
    }


    public Packet peek() {
        return headNode.getPacket();
    }

    private boolean isEmpty() {
        return currentSize == 0;
    }

    public Packet poll() {
        Node temp = headNode;
        headNode = temp.getNext();
        currentSize--;
        return temp.getPacket();
    }

    public int size() {
        return this.currentSize;
    }

    class Node {
        private Packet packet;
        private Node nextNode;

        public Node(Packet packet) {
            this.packet = packet;
        }

        public Packet getPacket() {
            return packet;
        }


        public Node getNext() {
            return nextNode;
        }

        public void setNext(Node nextNode) {
            this.nextNode = nextNode;
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int sizeBuffer = in.nextInt();
        int countPacket = in.nextInt();
        int[] result = new int[countPacket];
        int time = 0;
        NetworkPacket networkPacket = new NetworkPacket(sizeBuffer);

        for (int i = 0; i < countPacket; i++) {
            Packet packet = new Packet(in.nextInt(), in.nextInt());
            if (networkPacket.addPacket(packet)) {
                if (time < packet.getArrival()) time = packet.getArrival();
                result[i] = time; // начало выполнения i-го пакета
                time += packet.getDuration();
                packet.setFinish(time); // завершение выполнения пакета
            } else {
                if (packet.getArrival() >= networkPacket.peek().getFinish()) {
                    networkPacket.poll();
                    networkPacket.addPacket(packet);
                    if (time < packet.getArrival()) time = packet.getArrival();
                    result[i] = time;
                    time += packet.getDuration();
                    packet.setFinish(time);
                } else result[i] = -1;

            }


        }
        for (int i = 0; i < countPacket; i++) {
            System.out.println(result[i]);
        }
    }
}


class Packet {
    private int arrival;
    private int duration;
    private int finish;


    public Packet(int arrival, int duration) {
        this.arrival = arrival;
        this.duration = duration;
    }

    public int getFinish() {
        return finish;
    }

    public void setFinish(int finish) {
        this.finish = finish;
    }

    public int getArrival() {
        return arrival;
    }

    public int getDuration() {
        return duration;
    }


    @Override
    public String toString() {
        return "Packet: start time " + this.arrival + "; duration time " + this.duration;
    }
}

class QueuePacket {
    private Stack<Packet> leftStack;
    private Stack<Packet> rightStack;
    private int maxSize;
    private int currentSize = 0;


    public QueuePacket(int maxSize) {
        this.maxSize = maxSize;
    }

    public void push(Packet packet) {
        if (currentSize <= maxSize) {
            leftStack.push(packet);
            currentSize++;
        }
    }

    public Packet pop() {
        if (rightStack.isEmpty()) {
            while (!leftStack.isEmpty()) {
                rightStack.push(leftStack.pop());
            }
        }
        currentSize--;
        return rightStack.pop();
    }

}