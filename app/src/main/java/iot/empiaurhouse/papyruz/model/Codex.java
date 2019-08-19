package iot.empiaurhouse.papyruz.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.util.Objects;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "codex_table", foreignKeys = @ForeignKey(entity = Category.class,
        parentColumns = "id", childColumns = "category_id", onDelete = CASCADE))

public class Codex extends BaseObservable {


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "codex_id")
    private int codexId;

    @ColumnInfo(name = "codex_name")
    private String codexName;

    @ColumnInfo(name = "unit_price")
    private String unitPrice;

    @ColumnInfo(name = "unit_value")
    private String unitValue;

    @ColumnInfo(name = "codex_text")
    private String codexText;

    @ColumnInfo(name = "category_id")
    private int categoryId;


    public Codex() {
    }

    public Codex(int codexId, String codexName, String unitPrice, String unitValue, String codexText, int categoryId) {
        this.codexId = codexId;
        this.codexName = codexName;
        this.unitPrice = unitPrice;
        this.unitValue = unitValue;
        this.codexText = codexText;
        this.categoryId = categoryId;
    }


    @Bindable
    public int getCodexId() {
        return codexId;
    }

    public void setCodexId(int codexId) {
        this.codexId = codexId;
    }


    @Bindable
    public String getCodexName() {
        return codexName;
    }

    public void setCodexName(String codexName) {
        this.codexName = codexName;


    }


    @Bindable
    public String getCodexText() {
        return codexText;
    }

    public void setCodexText(String codexText) {
        this.codexText = codexText;


    }


    @Bindable
    public String getUnitValue() {
        return unitValue;
    }

    public void setUnitValue(String unitValue) {
        this.unitValue = unitValue;
    }



    @Bindable
    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }


    @Bindable
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;

    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Codex)) return false;
        Codex codex = (Codex) o;
        return getCodexId() == codex.getCodexId() &&
                getCategoryId() == codex.getCategoryId() &&
                Objects.equals(getCodexName(), codex.getCodexName()) &&
                Objects.equals(getCodexText(), codex.getCodexText()) &&
                Objects.equals(getUnitPrice(), codex.getUnitPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCodexId(), getCodexName(), getCodexText(), getUnitPrice(), getCategoryId());
    }


}
