public class Operation {
    String description;
    Double income;
    Double expense;

    public Operation(String description, Double income, Double expense){
        this.description = description;
        this.income = income;
        this.expense = expense;
    }

    public String getDescription() {
        return description;
    }

    public Double getIncome() {
        return income;
    }

    public Double getExpense() {
        return expense;
    }
}
