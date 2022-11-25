package app.quizeez.entity;

public abstract class Entity {
    
    public abstract Object[] toInsertData();

    public abstract Object[] toUpdateData();

    public abstract Object[] toDeleteData();
}
