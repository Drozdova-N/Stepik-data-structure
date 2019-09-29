package com.company;

public class SquareMatrix extends Matrix {

    public SquareMatrix(int size,boolean flag) {
        super(size, size);
    }

    public  int size(){
        return super.getCountRows();
    }
    @Override
    protected void addElements() {
        for (int i = 0; i < super.getCountRows(); i++) {
            for (int j = 0; j < super.getCountColumns(); j++) {
                super.setElement(i, j, 1);
            }
        }
    }
}
