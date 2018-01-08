package kz.greetgo.sandbox.controller.model;

public enum SortType {
  NON("id"), AGE("age"), TOTAL_SCORE("totalScore"), MAX_SCORE("maxScore"), MIN_SCORE("minScore");

  String columnName;

  SortType(String columnName){
    this.columnName = columnName;
  }

  public String getColumnName(){
    return columnName;
  }
}
