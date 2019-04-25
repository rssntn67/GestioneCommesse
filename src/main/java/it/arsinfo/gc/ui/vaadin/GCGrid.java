package it.arsinfo.gc.ui.vaadin;

import java.util.List;

import org.springframework.util.StringUtils;

import com.vaadin.data.ValueProvider;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Grid;

import it.arsinfo.gc.entity.GCEntity;

public abstract class GCGrid<T extends GCEntity>
        extends GCChangeHandler {

    /**
     * 
     */
    private static final long serialVersionUID = 8591152087754288177L;
    private final Grid<T> grid;
    private T selected;
    private final String gridName;
    
    public GCGrid(Grid<T> grid, String gridName) {

        this.grid = grid;
        this.gridName = gridName;
        this.grid.setWidth("100%");

        this.grid.asSingleSelect().addValueChangeListener(e -> {
            selected = e.getValue();
            onchange();
        });
    }
        

    public void setColumns(String...columnIds) {
        grid.setColumns(columnIds);
        if (!StringUtils.isEmpty(gridName)) {
            grid.prependHeaderRow().join(columnIds).setText(gridName);            
        }
    }

    public void setColumnCaption(String columnId, String caption) {
        if (grid.getColumn(columnId) == null) {
            return;
        }
        grid.getColumn(columnId).setCaption(caption);
    }
    
    public void populate(List<T> items) {
        if (items == null || items.size() == 0) {
            setVisible(false);
        } else {
            grid.setItems(items);
            setVisible(true);
        }
    }

    public T getSelected() {
        return selected;
    }


    public String getGridName() {
        return gridName;
    }
    
    public void addComponentColumn(ValueProvider<T,AbstractComponent> valueprovider) {
        grid.addComponentColumn(valueprovider);
    }
    
    public Grid<T> getGrid() {
        return grid;
    }
}
