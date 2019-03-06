package org.androidtown.mybudgeter.expendituretype;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "expenditure_type_table")
public class ExpenditureType {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String typeName;

    public ExpenditureType(String typeName) {
        this.typeName = typeName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
