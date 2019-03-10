package org.androidtown.mybudgeter.expenditure;

public class ProcessedExpenditure {

    private int id;
    private String memo;
    private String amount;

    public ProcessedExpenditure(int id, String memo, String amount) {
        this.id = id;
        this.memo = memo;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
