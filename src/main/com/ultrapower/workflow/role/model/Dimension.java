/*     */ package com.ultrapower.workflow.role.model;
/*     */ 
/*     */ import com.ultrapower.workflow.utils.UUIDGenerator;
/*     */ import java.io.Serializable;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.Table;
/*     */ 
/*     */ @Entity
/*     */ @Table(name="BS_T_WF_DIMENSIONS")
/*     */ public class Dimension
/*     */   implements Serializable
/*     */ {
/*     */   private String dimensionid;
/*     */   private String dimensioncode;
/*     */   private String dimensionname;
/*     */   private String dimCode;
/*     */   private String dimensiontype;
/*     */   private String tablename;
/*     */   private String tablecol;
/*     */   private String customSql;
/*     */   private String dtcode;
/*     */   private String divalue;
/*     */   private String fieldid;
/*     */   private String baseschema;
/*     */   private String basename;
/*     */   private String dictname;
/*     */   private String dictschema;
/*     */   private String dictfieldid;
/*     */   private String dictfieldcode;
/*     */   private String dictfieldname;
/*     */ 
/*     */   public Dimension()
/*     */   {
/* 100 */     this.dimensionid = UUIDGenerator.getId();
/*     */   }
/*     */ 
/*     */   public Dimension(String dimensionid)
/*     */   {
/* 105 */     this.dimensionid = dimensionid;
/*     */   }
/*     */ 
/*     */   public Dimension(String dimensionid, String dimensioncode, String dimensionname, String fieldid, String baseschema, String basename, String dictname, String dictschema, String dictfieldid, String dictfieldcode, String dictfieldname)
/*     */   {
/* 113 */     this.dimensionid = dimensionid;
/* 114 */     this.dimensioncode = dimensioncode;
/* 115 */     this.dimensionname = dimensionname;
/* 116 */     this.fieldid = fieldid;
/* 117 */     this.baseschema = baseschema;
/* 118 */     this.basename = basename;
/* 119 */     this.dictname = dictname;
/* 120 */     this.dictschema = dictschema;
/* 121 */     this.dictfieldid = dictfieldid;
/* 122 */     this.dictfieldcode = dictfieldcode;
/* 123 */     this.dictfieldname = dictfieldname;
/*     */   }
/*     */ 
/*     */   @Id
/*     */   public String getDimensionid() {
/* 129 */     return this.dimensionid;
/*     */   }
/*     */ 
/*     */   public void setDimensionid(String dimensionid) {
/* 133 */     this.dimensionid = dimensionid;
/*     */   }
/*     */   @Column(name="DIMENSIONCODE", unique=false, nullable=true, insertable=true, updatable=true, length=50)
/*     */   public String getDimensioncode() {
/* 138 */     return this.dimensioncode;
/*     */   }
/*     */ 
/*     */   public void setDimensioncode(String dimensioncode) {
/* 142 */     this.dimensioncode = dimensioncode;
/*     */   }
/*     */   @Column(name="DIMENSIONNAME", unique=false, nullable=true, insertable=true, updatable=true, length=100)
/*     */   public String getDimensionname() {
/* 147 */     return this.dimensionname;
/*     */   }
/*     */ 
/*     */   public void setDimensionname(String dimensionname) {
/* 151 */     this.dimensionname = dimensionname;
/*     */   }
/*     */   @Column(name="FIELDID", unique=false, nullable=true, insertable=true, updatable=true, length=15)
/*     */   public String getFieldid() {
/* 156 */     return this.fieldid;
/*     */   }
/*     */ 
/*     */   public void setFieldid(String fieldid) {
/* 160 */     this.fieldid = fieldid;
/*     */   }
/*     */   @Column(name="BASESCHEMA", unique=false, nullable=true, insertable=true, updatable=true, length=50)
/*     */   public String getBaseschema() {
/* 165 */     return this.baseschema;
/*     */   }
/*     */ 
/*     */   public void setBaseschema(String baseschema) {
/* 169 */     this.baseschema = baseschema;
/*     */   }
/*     */   @Column(name="BASENAME", unique=false, nullable=true, insertable=true, updatable=true, length=200)
/*     */   public String getBasename() {
/* 174 */     return this.basename;
/*     */   }
/*     */ 
/*     */   public void setBasename(String basename) {
/* 178 */     this.basename = basename;
/*     */   }
/*     */   @Column(name="DICTNAME", unique=false, nullable=true, insertable=true, updatable=true, length=200)
/*     */   public String getDictname() {
/* 183 */     return this.dictname;
/*     */   }
/*     */ 
/*     */   public void setDictname(String dictname) {
/* 187 */     this.dictname = dictname;
/*     */   }
/*     */   @Column(name="DICTSCHEMA", unique=false, nullable=true, insertable=true, updatable=true, length=50)
/*     */   public String getDictschema() {
/* 192 */     return this.dictschema;
/*     */   }
/*     */ 
/*     */   public void setDictschema(String dictschema) {
/* 196 */     this.dictschema = dictschema;
/*     */   }
/*     */   @Column(name="DICTFIELDID", unique=false, nullable=true, insertable=true, updatable=true, length=15)
/*     */   public String getDictfieldid() {
/* 201 */     return this.dictfieldid;
/*     */   }
/*     */ 
/*     */   public void setDictfieldid(String dictfieldid) {
/* 205 */     this.dictfieldid = dictfieldid;
/*     */   }
/*     */   @Column(name="DICTFIELDCODE", unique=false, nullable=true, insertable=true, updatable=true, length=50)
/*     */   public String getDictfieldcode() {
/* 210 */     return this.dictfieldcode;
/*     */   }
/*     */ 
/*     */   public void setDictfieldcode(String dictfieldcode) {
/* 214 */     this.dictfieldcode = dictfieldcode;
/*     */   }
/*     */   @Column(name="DICTFIELDNAME", unique=false, nullable=true, insertable=true, updatable=true, length=100)
/*     */   public String getDictfieldname() {
/* 219 */     return this.dictfieldname;
/*     */   }
/*     */ 
/*     */   public void setDictfieldname(String dictfieldname) {
/* 223 */     this.dictfieldname = dictfieldname;
/*     */   }
/*     */ 
/*     */   public String getDimCode() {
/* 227 */     return this.dimCode;
/*     */   }
/*     */ 
/*     */   public void setDimCode(String dimCode) {
/* 231 */     this.dimCode = dimCode;
/*     */   }
/*     */ 
/*     */   public String getDimensiontype()
/*     */   {
/* 236 */     return this.dimensiontype;
/*     */   }
/*     */ 
/*     */   public void setDimensiontype(String dimensiontype)
/*     */   {
/* 241 */     this.dimensiontype = dimensiontype;
/*     */   }
/*     */ 
/*     */   public String getTablename()
/*     */   {
/* 246 */     return this.tablename;
/*     */   }
/*     */ 
/*     */   public void setTablename(String tablename)
/*     */   {
/* 251 */     this.tablename = tablename;
/*     */   }
/*     */ 
/*     */   public String getTablecol()
/*     */   {
/* 256 */     return this.tablecol;
/*     */   }
/*     */ 
/*     */   public void setTablecol(String tablecol)
/*     */   {
/* 261 */     this.tablecol = tablecol;
/*     */   }
/*     */ 
/*     */   public String getDtcode()
/*     */   {
/* 266 */     return this.dtcode;
/*     */   }
/*     */ 
/*     */   public void setDtcode(String dtcode)
/*     */   {
/* 271 */     this.dtcode = dtcode;
/*     */   }
/*     */ 
/*     */   public String getDivalue()
/*     */   {
/* 276 */     return this.divalue;
/*     */   }
/*     */ 
/*     */   public void setDivalue(String divalue)
/*     */   {
/* 281 */     this.divalue = divalue;
/*     */   }
/*     */ 
/*     */   public String getCustomSql() {
/* 285 */     return this.customSql;
/*     */   }
/*     */ 
/*     */   public void setCustomSql(String customSql) {
/* 289 */     this.customSql = customSql;
/*     */   }
/*     */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.role.model.Dimension
 * JD-Core Version:    0.6.0
 */