package fileImport;


import java.io.InputStream;

/**
 * @author JIE WU
 * @create 2018-06-06
 * @desc 文件描述类
 **/
public class FileImportDescriptor {
    private String fileType;
    private String fileName;
    private InputStream inputStream;
    // fieldName所在行数
    private int fieldRow;
    // 数据开始行数，必须大于fieldRow
    private int dataStartRow;

    public int getDataStartRow() {
        return dataStartRow;
    }

    public void setDataStartRow(int dataStartRow) {
        this.dataStartRow = dataStartRow;
    }

    public int getFieldRow() {
        return fieldRow;
    }

    public void setFieldRow(int fieldRow) {
        this.fieldRow = fieldRow;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

}
