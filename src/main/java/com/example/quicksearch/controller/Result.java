package com.example.quicksearch.controller;

/**


 * 学习git 2213
 * 学习git 2213
 * @param <T>
 * @param <T>
 */
public class Result<T> {
    private Integer code = 200;

    private String msg;

    private T data;

    public Result() {

    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Result<T> success() {
        return new Result<>(200, "");
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMsg("");
        result.setData(data);
        return result;
    }

    public static <T> Result<T> success(String msg, T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> success(Integer code, String msg) {
        return new Result<>(code, msg);
    }


    public static <T> Result<T> failure(String msg) {
        return new Result<>(500, msg);
    }


    public static <T> Result<T> failure(Integer code, String msg) {
        return new Result<>(code, msg);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
