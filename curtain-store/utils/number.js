/**
 * @Description 数值计算
 * @author coisini
 * @date Aug 30, 2021
 * @Version 1.0
 */

/**
 * 加 +
 * @param num1
 * @param num2
 * @returns {number}
 */
function accAddition(num1, num2) {
    const num1Digits = (num1.toString().split('.')[1] || '').length;
    const num2Digits = (num2.toString().split('.')[1] || '').length;
    const baseNum = Math.pow(10, Math.max(num1Digits, num2Digits));
    return (Math.round(num1 * baseNum) + Math.round(num2 * baseNum)) / baseNum;
}

/**
 * 减 -
 * @param num1
 * @param num2
 * @returns {number}
 */
function accSubtract(num1, num2) {
    const num1Digits = (num1.toString().split('.')[1] || '').length;
    const num2Digits = (num2.toString().split('.')[1] || '').length;
    const baseNum = Math.pow(10, Math.max(num1Digits, num2Digits));
    return (Math.round(num1 * baseNum) - Math.round(num2 * baseNum)) / baseNum;
}

/**
 * 乘 *
 * @param num1
 * @param num2
 * @returns {number}
 */
function accMultiply(num1, num2) {
    const num1Digits = (num1.toString().split('.')[1] || '').length;
    const num2Digits = (num2.toString().split('.')[1] || '').length;
    const baseNum = Math.pow(10, Math.max(num1Digits, num2Digits));
    return (Math.round(num1 * baseNum) * Math.round(num2 * baseNum)) / baseNum / baseNum;
}

/**
 * 除 /
 * @param num1
 * @param num2
 * @returns {number}
 */
function accDivision(num1, num2) {
    const num1Digits = (num1.toString().split('.')[1] || '').length;
    const num2Digits = (num2.toString().split('.')[1] || '').length;
    const baseNum = Math.pow(10, Math.max(num1Digits, num2Digits));
    return (Math.round(num1 * baseNum) / Math.round(num2 * baseNum));
}

export {
    accAddition,
    accMultiply,
    accSubtract,
    accDivision
}