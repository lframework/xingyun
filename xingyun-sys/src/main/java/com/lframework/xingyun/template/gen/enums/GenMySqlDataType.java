package com.lframework.xingyun.template.gen.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

/**
 * MySql列类型枚举 dataType为null时表示该类型暂不支持
 */
public enum GenMySqlDataType implements BaseEnum<String> {
  TINYINT("tinyint", GenDataType.INTEGER, "tinyint"), SMALLINT("smallint", GenDataType.INTEGER,
      "smallint"), MEDIUMINT("mediumint", GenDataType.INTEGER, "mediumint"), INT("int",
      GenDataType.INTEGER,
      "int"), INTEGER("integer", GenDataType.INTEGER, "integer"), BIGINT("bigint", GenDataType.LONG,
      "bigint"), BIT("bit", GenDataType.BOOLEAN, "bit"), REAL("real", GenDataType.DOUBLE,
      "real"), DOUBLE(
      "double", GenDataType.BIG_DECIMAL, "double"), FLOAT("float", GenDataType.BIG_DECIMAL,
      "float"), DECIMAL(
      "decimal", GenDataType.BIG_DECIMAL, "decimal"), NUMERIC("numeric", GenDataType.BIG_DECIMAL,
      "numeric"), CHAR("char", GenDataType.STRING, "char"), VARCHAR("varchar", GenDataType.STRING,
      "varchar"), DATE("date", GenDataType.LOCAL_DATE, "date"), TIME("time", GenDataType.LOCAL_TIME,
      "time"), YEAR("year", null, "year"), TIMESTAMP("timestamp", GenDataType.LOCAL_DATE_TIME,
      "timestamp"), DATETIME("datetime", GenDataType.LOCAL_DATE_TIME, "datetime"), TINYBLOB(
      "tinyblob", null,
      "tinyblob"), BLOB("blob", null, "blob"), MEDIUMBLOB("mediumblob", null,
      "mediumblob"), LONGBLOB("longblob",
      null, "longblob"), TINYTEXT("tinytext", GenDataType.STRING, "tinytext"), MEDIUMTEXT(
      "mediumtext",
      GenDataType.STRING, "mediumtext"), TEXT("text", GenDataType.STRING, "text"), LONGTEXT(
      "longtext",
      GenDataType.STRING, "longtext"), ENUM("enum", GenDataType.STRING, "enum"), SET("set",
      GenDataType.STRING,
      "set"), BINARY("binary", null, "binary"), VARBINARY("varbinary", null, "varbinary"), POINT(
      "point", null,
      "point"), LINESTRING("linestring", null, "linestring"), POLYGON("polygon", null,
      "polygon"), GEOMETRY(
      "geometry", null, "geometry"), MULTIPOINT("multipoint", null, "multipoint"), MULTILINESTRING(
      "multilinestring", null, "multilinestring"), MULTIPOLYGON("multipolygon", null,
      "multipolygon"), GEOMETRYCOLLECTION("geometrycollection", null, "geometrycollection"), JSON(
      "json", null,
      "json");

  @EnumValue
  private final String code;

  private final GenDataType dataType;

  private final String desc;

  GenMySqlDataType(String code, GenDataType dataType, String desc) {

    this.code = code;
    this.dataType = dataType;
    this.desc = desc;
  }

  @Override
  public String getCode() {

    return this.code;
  }

  @Override
  public String getDesc() {

    return this.desc;
  }

  public GenDataType getDataType() {

    return dataType;
  }
}
