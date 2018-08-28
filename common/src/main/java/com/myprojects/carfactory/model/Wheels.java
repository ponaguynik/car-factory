package com.myprojects.carfactory.model;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.annotation.Nullable;
import java.util.AbstractCollection;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Wheels extends CarPart {
    @JsonSerialize(converter = TableToSetCellConverter.class)
    @JsonDeserialize(converter = CellSetToTableConverter.class)
    private Table<Integer, Integer, Wheel> layout;

    public Wheels wheel(int row, int col, Wheel wheel) {
        layout.put(row, col, wheel);
        return this;
    }

    protected Wheels() {
        this.layout = HashBasedTable.create();
    }

    public static Wheels create() {
        return new Wheels();
    }

    @Override
    public void setAssemblyNumber(String assemblyNumber) {
        super.setAssemblyNumber(assemblyNumber);
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

    public static class CellSetToTableConverter implements Converter<Set<WheelCell>, Table<Integer, Integer, Wheel>> {

        @Override
        public Table<Integer, Integer, Wheel> convert(Set<WheelCell> value) {
            return value.stream()
                    .collect(HashBasedTable::create,
                            (table, cell) -> table.put(cell.getRow(), cell.getCol(), cell.getWheel()),
                            (table1, table2) -> table1.putAll(table2));
        }

        @Override
        public JavaType getInputType(TypeFactory typeFactory) {
            return typeFactory.constructCollectionType(Set.class, WheelCell.class);
        }

        @Override
        public JavaType getOutputType(TypeFactory typeFactory) {
            return typeFactory.constructType(Table.class);
        }
    }

    public static class TableToSetCellConverter implements Converter<Table<Integer, Integer, Wheel>, Set<WheelCell>> {

        @Override
        public Set<WheelCell> convert(Table<Integer, Integer, Wheel> value) {
            return value.cellSet().stream()
                    .collect(HashSet::new,
                            (set, cell) -> set.add(new WheelCell(cell.getRowKey(), cell.getColumnKey(), cell.getValue())),
                            AbstractCollection::addAll);
        }

        @Override
        public JavaType getInputType(TypeFactory typeFactory) {
            return typeFactory.constructType(Table.class);
        }

        @Override
        public JavaType getOutputType(TypeFactory typeFactory) {
            return typeFactory.constructCollectionType(Set.class, WheelCell.class);
        }
    }
}
