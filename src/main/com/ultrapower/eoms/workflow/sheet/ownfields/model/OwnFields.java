package com.ultrapower.eoms.workflow.sheet.ownfields.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "BS_T_WF_BASEOWNFIELDS")
public class OwnFields implements java.io.Serializable {
	
	private String id;
	private String baseSchema;
	private String baseName;
	private String fieldId;
	private String fieldCode;
	private String fieldName;
	private String fieldType;
	private String fieldTypeValue;
	private long fieldIsToLong;
	private String freeEditStep;
	private String defEditStep;
	private long printOneLine;
	private long printOrder;
	private long isPrint;
	private long createTime;
	private long logPosition;
	
	public OwnFields() {
	}
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getBaseSchema() {
		return baseSchema;
	}

	public void setBaseSchema(String baseSchema) {
		this.baseSchema = baseSchema;
	}

	public String getBaseName() {
		return baseName;
	}

	public void setBaseName(String baseName) {
		this.baseName = baseName;
	}

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public String getFieldCode() {
		return fieldCode;
	}

	public void setFieldCode(String fieldCode) {
		this.fieldCode = fieldCode;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getFieldTypeValue() {
		return fieldTypeValue;
	}

	public void setFieldTypeValue(String fieldTypeValue) {
		this.fieldTypeValue = fieldTypeValue;
	}

	public long getFieldIsToLong() {
		return fieldIsToLong;
	}

	public void setFieldIsToLong(long fieldIsToLong) {
		this.fieldIsToLong = fieldIsToLong;
	}

	public String getFreeEditStep() {
		return freeEditStep;
	}

	public void setFreeEditStep(String freeEditStep) {
		this.freeEditStep = freeEditStep;
	}

	public String getDefEditStep() {
		return defEditStep;
	}

	public void setDefEditStep(String defEditStep) {
		this.defEditStep = defEditStep;
	}

	public long getPrintOneLine() {
		return printOneLine;
	}

	public void setPrintOneLine(long printOneLine) {
		this.printOneLine = printOneLine;
	}

	public long getPrintOrder() {
		return printOrder;
	}

	public void setPrintOrder(long printOrder) {
		this.printOrder = printOrder;
	}

	public long getIsPrint() {
		return isPrint;
	}

	public void setIsPrint(long isPrint) {
		this.isPrint = isPrint;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getLogPosition() {
		return logPosition;
	}

	public void setLogPosition(long logPosition) {
		this.logPosition = logPosition;
	}

}
