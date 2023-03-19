package com.api.jpa.board.Enum;

public enum AccountType {
    OUT("OUT", "외부사용자"),
    REALTOR("REALTOR", "공인중개사"),
    LESSOR("LESSOR", "임대인"),
    LESSEE("LESSEE", "임차인");

    private String code;
    private String role;

    AccountType(String code, String role){
        this.code = code;
        this.role = role;
    }

    public String getCode() {
        return code;
    }

    public static AccountType get(String code){
        for(AccountType item : values()){
            if(code.equals(item.getCode())){
                return item;
            }
        }
        return OUT;
    }

}
