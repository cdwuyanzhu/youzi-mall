package com.crms.utils;

public class DataResultsPage extends DataResults {

    private Long count;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public static DataResultsPage success(ResultCode resultCode, Object data,String msg,Long count){
        DataResultsPage result = new DataResultsPage();
        result.setCode(resultCode.getCode());
        result.setData(data);
        result.setMsg(msg);
        result.setCount(count);
        return result;
    }
}
