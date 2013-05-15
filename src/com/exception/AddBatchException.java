package com.exception;

public class AddBatchException extends Exception
{
    private static final long serialVersionUID = -5862851861961604168L;
    private String errorType;
    private int errorRow;
    private String srcRow;

    public AddBatchException()
    {
    }

    public AddBatchException(String errorType)
    {
        this.errorType = errorType;
    }
    
    public AddBatchException(String errorType, int errorRow)
    {
        this.errorType = errorType;
        this.errorRow = errorRow;
    }
    public String getErrorType()
    {
        return errorType;
    }

    public void setErrorType(String errorType)
    {
        this.errorType = errorType;
    }

    public int getErrorRow()
    {
        return errorRow;
    }

    public void setErrorRow(int errorRow)
    {
        this.errorRow = errorRow;
    }

    public String getSrcRow()
    {
        return srcRow;
    }

    public void setSrcRow(String srcRow)
    {
        this.srcRow = srcRow;
    }
}