/**
 * @Description HTTP Exception
 * @author coisini
 * @date Sep 2, 2021
 * @Version 1.0
 */
export class HttpException extends Error {

    errorCode = 9999
    statusCode = 500
    message = ''

    constructor(errorCode, message, statusCode) {
        super()
        this.message = message;
        this.errorCode = errorCode;
        this.statusCode = statusCode;
    }

}
