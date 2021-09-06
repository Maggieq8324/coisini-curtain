/**
 * @Description OrderException
 * @author coisini
 * @date Sep 1, 2021
 * @Version 1.0
 */
export class OrderException extends Error {

    type

    constructor(msg, type) {
        super()
        this.message = msg
        this.type = type
    }

}