package com.example.combinacoesderesultados.enums;


import lombok.Getter;

@Getter
public enum PontosEnum {
    Touchdown(6),
    Extratouchdown7(7),
    Extratouchdown8(8),
    Fieldgoal(3);

    private Integer value;

    PontosEnum(Integer value) {this.value = value;}

}
