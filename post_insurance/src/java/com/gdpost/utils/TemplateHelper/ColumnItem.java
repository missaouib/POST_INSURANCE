package com.gdpost.utils.TemplateHelper;

public class ColumnItem {
	// 中文列名
	private String displayName = "";
	// 标准列名
	private String columnName = "";
	// 列类型，字符型、数值型
	private ColumnType columnType = ColumnType.string;
	// 是否可以为null
	private boolean nullable = true;
	// 是否对应列
	private boolean isMapColumn = true;
	// 对应列序号
	private int mapColumnIndex = 0;
	// 是否需要多列计算
	private boolean needCalculate = false;
	// 多列计算公式
	private String formula = "";
	// 是否从文件名中获取
	private boolean isFromFileName = false;
	// 是否从Sheet名中获取 
	private boolean isFromSheetName = false;
	// 是否从列中获取 
	private boolean isFromColumn = false;
	// 从列名中截取的列序号
	private int fromColumnIndex = 0;
	// 是否需要分割
	private boolean needConcat = false;
	// 分割规则
	private ConcatRule concatRule = null;
	// 是否新增数据列
	private boolean isAddColumn = false;
	// 从哪列列名新增
	private String relateColumnName = "";
	// 相关的数据表名
	private String relateTableName = "";
	// 相关的数据表的列名
	private String relateTableColumnName = "";
	// 标准列是否有值，是否需要处理
	private boolean hasValue = false;
	// 是否需要加密
	private boolean needEncrypt = false;
	private boolean isStaticValue = false;
	private String staticValue = "";
	// 列名
	private String mapColumnName = "";
	private boolean needOutput = false;
	private Integer iOutputOrder = 1000;
	private String outputName = "";
	
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public ColumnType getColumnType() {
		return columnType;
	}
	public void setColumnType(ColumnType columnType) {
		this.columnType = columnType;
	}
	public boolean isNullable() {
		return nullable;
	}
	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}
	public int getMapColumnIndex() {
		return mapColumnIndex;
	}
	public void setMapColumnIndex(int mapColumnIndex) {
		this.mapColumnIndex = mapColumnIndex;
	}
	public boolean isNeedCalculate() {
		return needCalculate;
	}
	public void setNeedCalculate(boolean needCalculate) {
		this.needCalculate = needCalculate;
	}
	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		this.formula = formula;
	}
	public boolean isFromFileName() {
		return isFromFileName;
	}
	public void setFromFileName(boolean isFromFileName) {
		this.isFromFileName = isFromFileName;
	}
	public boolean isFromSheetName() {
		return isFromSheetName;
	}
	public void setFromSheetName(boolean isFromSheetName) {
		this.isFromSheetName = isFromSheetName;
	}
	public boolean isFromColumn() {
		return isFromColumn;
	}
	public void setFromColumn(boolean isFromColumnName) {
		this.isFromColumn = isFromColumnName;
	}
	public boolean isNeedConcat() {
		return needConcat;
	}
	public void setNeedConcat(boolean needConcat) {
		this.needConcat = needConcat;
	}
	public ConcatRule getConcatRule() {
		return concatRule;
	}
	public void setConcatRule(ConcatRule concatRule) {
		this.concatRule = concatRule;
	}
	public boolean isMapColumn() {
		return isMapColumn;
	}
	public void setMapColumn(boolean isMapColumn) {
		this.isMapColumn = isMapColumn;
	}
	public int getFromColumnIndex() {
		return fromColumnIndex;
	}
	public void setFromColumnIndex(int fromColumnIndex) {
		this.fromColumnIndex = fromColumnIndex;
	}
	public boolean isAddColumn() {
		return isAddColumn;
	}
	public void setAddColumn(boolean isAddColumn) {
		this.isAddColumn = isAddColumn;
	}
	public String getRelateColumnName() {
		return relateColumnName;
	}
	public void setRelateColumnName(String relateColumnName) {
		this.relateColumnName = relateColumnName;
	}
	public String getRelateTableName() {
		return relateTableName;
	}
	public void setRelateTableName(String relateTableName) {
		this.relateTableName = relateTableName;
	}
	public String getRelateTableColumnName() {
		return relateTableColumnName;
	}
	public void setRelateTableColumnName(String relateTableColumnName) {
		this.relateTableColumnName = relateTableColumnName;
	}
	public boolean isHasValue() {
		return hasValue;
	}
	public void setHasValue(boolean hasValue) {
		this.hasValue = hasValue;
	}
	public boolean isNeedEncrypt() {
		return needEncrypt;
	}
	public void setNeedEncrypt(boolean needEncrypt) {
		this.needEncrypt = needEncrypt;
	}
	public boolean isStaticValue() {
		return isStaticValue;
	}
	public void setIsStaticValue(boolean isStaticValue) {
		this.isStaticValue = isStaticValue;
	}
	public String getStaticValue() {
		return staticValue;
	}
	public void setStaticValue(String staticValue) {
		this.staticValue = staticValue;
	}
	public String getMapColumnName() {
		return mapColumnName;
	}
	public void setMapColumnName(String mapColumnName) {
		this.mapColumnName = mapColumnName;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public boolean isNeedOutput() {
		return needOutput;
	}
	public void setNeedOutput(boolean needOutput) {
		this.needOutput = needOutput;
	}
	public Integer getiOutputOrder() {
		return iOutputOrder;
	}
	public void setiOutputOrder(Integer iOutputOrder) {
		this.iOutputOrder = iOutputOrder;
	}
	public String getOutputName() {
		return outputName;
	}
	public void setOutputName(String outputName) {
		this.outputName = outputName;
	}
	
}

