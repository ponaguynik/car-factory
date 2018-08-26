package com.myprojects.carfactory.model;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.annotation.Nullable;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Wheels extends CarPart {
    private final Table<Integer, Integer, Wheel> layout = HashBasedTable.create();

    public Wheels wheel(int row, int col, Wheel wheel) {
        layout.put(row, col, wheel);
        return this;
    }

    public static Wheels create() {
        return new Wheels();
    }

    public void setAssemblyNumber(String assemblyNumber) {
        this.assemblyNumber = assemblyNumber;
        layout.cellSet().stream()
                .map(Table.Cell::getValue)
                .forEach(wheel -> wheel.setAssemblyNumber(assemblyNumber));
    }

    public Wheel removeFromPosition(int row, int col) {
        return layout.remove(row, col);
    }

    @Nullable
    public Wheel getAtPosition(int row, int col) {
        return layout.get(row, col);
    }

    public void reset() {
        layout.clear();
    }

    public Table<Integer, Integer, Wheel> getLayout() {
        return layout;
    }
}
