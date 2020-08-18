package xyz.huanju.accounting.utils;

import java.math.BigDecimal;

/**
 * @author HuanJu
 * @date 2020/8/18 1:31
 */
public final class AccountBookUtils {



    public static int getMark(int category, BigDecimal dm, BigDecimal cm) {
        if (dm == null) {
            dm = new BigDecimal("0.00");
        }
        if (cm == null) {
            cm = new BigDecimal("0.00");
        }
        BigDecimal m = null;
        if (category == 1 || category == 7) {
            //资产+费用
            m = dm.subtract(cm);
            return dcMark(m);
        } else {
            //其他
            m = cm.subtract(dm);
            return cdMark(m);
        }
    }


    /**
     * 计算金额
     *
     * @param category 科目类别
     * @param m        上行金额
     * @param dm       借方金额
     * @param cm       贷方金额
     */
    public static BigDecimal computeMoney(int category, BigDecimal m, BigDecimal dm, BigDecimal cm) {

        if (category == 1 || category == 7) {
            return debitCredit(m, dm, cm);
        } else {
            return creditDebit(m, dm, cm);
        }
    }

    /**
     * 资产+费用科目计算
     * <p>
     * 金额=上期金额+借方金额-贷方金额
     */
    private static BigDecimal debitCredit(BigDecimal m, BigDecimal dm, BigDecimal cm) {
        if (m == null) {
            m = new BigDecimal("0.00");
        }
        if (dm != null) {
            m = m.add(dm);
        }
        if (cm != null) {
            m = m.subtract(cm);
        }
        return m;
    }


    /**
     * 负债+收入+所有权权益
     * <p>
     * 金额=上期金额+贷方金额-借方金额
     */
    private static BigDecimal creditDebit(BigDecimal money, BigDecimal debitMoney, BigDecimal creditMoney) {
        if (money == null) {
            money = new BigDecimal("0.00");
        }
        if (creditMoney != null) {
            money = money.add(creditMoney);
        }
        if (debitMoney != null) {
            money = money.subtract(debitMoney);
        }
        return money;
    }


    /**
     * 资产+费用科目计算
     */
    public static int dcMark(BigDecimal m) {
        if (m == null) {
            return 0;
        }
        double v = m.doubleValue();
        if (v > 0) {
            return -1;
        } else if (v < 0) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * 负债+收入+所有权权益
     */
    public static int cdMark(BigDecimal m) {
        if (m == null) {
            return 0;
        }
        double v = m.doubleValue();
        if (v > 0) {
            return 1;
        } else if (v < 0) {
            return -1;
        } else {
            return 0;
        }
    }





}
