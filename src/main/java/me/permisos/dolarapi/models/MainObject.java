package me.permisos.dolarapi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MainObject {

  @SerializedName("compra")
  @Expose
  public String compra;

  @SerializedName("venta")
  @Expose
  public String venta;

  @SerializedName("agencia")
  @Expose
  public String agencia;

  @SerializedName("nombre")
  @Expose
  public String nombre;

  @SerializedName("decimales")
  @Expose
  public String decimales;
}


