package shop.data;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import shop.command.UndoableCommand;

public class CompositeTest {
        final Video v1 = Data.newVideo("K1", 2003, "S1");
        final Video v2 = Data.newVideo("K2", 2002, "S2");
    @Test
    public void TestA(){
        
        final Inventory inventory = Data.newInventory();
        UndoableCommand c1 = Data.newAddCmd(inventory, v1, 2);
        UndoableCommand c2 = Data.newAddCmd(inventory, v2, 3);
        Composite c = new Composite();
        c.add(c1); c.add(c2);

        assertEquals( 0, inventory.size() );
        c.run();
        assertEquals( 2, inventory.size() );
        c.undo();
        assertEquals( 0, inventory.size() );
        c.redo();
        assertEquals( 2, inventory.size() );
    }

    @Test
    public void TestB(){
        final Inventory inventory = Data.newInventory();
        UndoableCommand c1 = Data.newAddCmd(inventory, v1, 2);
        UndoableCommand c2 = Data.newAddCmd(inventory, v2, 3);
        UndoableCommand c3 = Data.newOutCmd(inventory, v1);
        Composite d = new Composite();
        d.add(c3); d.add(c2);

        assertEquals( 0, inventory.size() );
        c1.run();
        assertEquals( 1, inventory.size() );
        d.run();
        assertEquals( 2, inventory.size() );
        assertEquals( "VideoObj[title=K1, year=2003, director=S1] [total copies: 2, currently checked out: 1, total rentals: 1]", inventory.get(v1).toString() );
        d.undo();
        assertEquals( 1, inventory.size() );
        assertEquals( "VideoObj[title=K1, year=2003, director=S1] [total copies: 2, currently checked out: 0, total rentals: 0]", inventory.get(v1).toString() );
    }

    @Test
    public void TestC(){
        final Inventory inventory = Data.newInventory();

        UndoableCommand c1 = Data.newAddCmd(inventory, v1, 2);
        UndoableCommand c2 = Data.newAddCmd(inventory, v2, 3);
        Composite c = new Composite();
        c.add(c1); c.add(c2);
        
        // UndoableCommand c2 = Data.newAddCmd(inventory, v2, 3);
        UndoableCommand c3 = Data.newOutCmd(inventory, v1);
        Composite d = new Composite();
        d.add(c3); d.add(c2);
        
        Composite  e = new Composite();
        e.add(c); e.add(d);
        assertEquals( 0, inventory.size() );
        e.run();
        assertEquals( 2, inventory.size() );
        assertEquals( "VideoObj[title=K1, year=2003, director=S1] [total copies: 2, currently checked out: 1, total rentals: 1]", inventory.get(v1).toString() );
        assertEquals( "VideoObj[title=K2, year=2002, director=S2] [total copies: 3, currently checked out: 0, total rentals: 0]", inventory.get(v2).toString() );
    }
}
