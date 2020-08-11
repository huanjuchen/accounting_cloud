package xyz.huanju.accounting.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author HuanJu
 * @date 2020/8/8 20:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CommonResult<T> {

    /**
     *
     */
    private Integer code;

    private String message;

    private T data;

    public static <T> CommonResult<T> ok() {
        return new CommonResult<T>(ResultCode.OK, null, null);
    }

    public static <T> CommonResult<T> ok(T data) {
        return new CommonResult<T>(ResultCode.OK, null, data);
    }


}
