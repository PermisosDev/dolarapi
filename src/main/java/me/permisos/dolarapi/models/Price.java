package me.permisos.dolarapi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Price {

  @SerializedName("casa")
  @Expose
  public MainObject exchange_rate;
}

