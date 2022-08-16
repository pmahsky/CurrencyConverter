package com.app.currency_converter.data.network.response

import com.google.gson.annotations.SerializedName

data class ExchangeRateResponse(
    @SerializedName("base")
    val base: String?,
    @SerializedName("rates")
    val exchangeRates: ExchangeRates?,
    @SerializedName("timestamp")
    val timestamp: Int?
)

/*class ExchangeRates(
    @SerializedName("AED")
    var USD_AED: Double = 0.0,

    @SerializedName("AFN")
    var USD_AFN: Double = 0.0,

    @SerializedName("ALL")
    var USD_ALL: Double = 0.0,

    @SerializedName("AMD")
    var USD_AMD: Double = 0.0,

    @SerializedName("ANG")
    var USD_ANG: Double = 0.0,

    @SerializedName("AOA")
    var USD_AOA: Double = 0.0,

    @SerializedName("ARS")
    var USD_ARS: Double = 0.0,

    @SerializedName("AUD")
    var USD_AUD: Double = 0.0,

    @SerializedName("AWG")
    var USD_AWG: Double = 0.0,

    @SerializedName("AZN")
    var USD_AZN: Double = 0.0,

    @SerializedName("BAM")
    var USD_BAM: Double = 0.0,

    @SerializedName("BBD")
    var USD_BBD: Double = 0.0,

    @SerializedName("BDT")
    var USD_BDT: Double = 0.0,

    @SerializedName("BGN")
    var USD_BGN: Double = 0.0,

    @SerializedName("BHD")
    var USD_BHD: Double = 0.0,

    @SerializedName("BIF")
    var USD_BIF: Double = 0.0,

    @SerializedName("BMD")
    var USD_BMD: Double = 0.0,

    @SerializedName("BND")
    var USD_BND: Double = 0.0,

    @SerializedName("BOB")
    var USD_BOB: Double = 0.0,

    @SerializedName("BRL")
    var USD_BRL: Double = 0.0,

    @SerializedName("BSD")
    var USD_BSD: Double = 0.0,

    @SerializedName("BTC")
    var USD_BTC: Double = 0.0,

    @SerializedName("BTN")
    var USD_BTN: Double = 0.0,

    @SerializedName("BWP")
    var USD_BWP: Double = 0.0,

    @SerializedName("BYN")
    var USD_BYN: Double = 0.0,

    @SerializedName("BZD")
    var USD_BZD: Double = 0.0,

    @SerializedName("CAD")
    var USD_CAD: Double = 0.0,

    @SerializedName("CDF")
    var USD_CDF: Double = 0.0,

    @SerializedName("CHF")
    var USD_CHF: Double = 0.0,

    @SerializedName("CLP")
    var USD_CLP: Double = 0.0,

    @SerializedName("CNY")
    var USD_CNY: Double = 0.0,

    @SerializedName("COP")
    var USD_COP: Double = 0.0,

    @SerializedName("CRC")
    var USD_CRC: Double = 0.0,

    @SerializedName("CUP")
    var USD_CUP: Double = 0.0,

    @SerializedName("CVE")
    var USD_CVE: Double = 0.0,

    @SerializedName("CZK")
    var USD_CZK: Double = 0.0,

    @SerializedName("DJF")
    var USD_DJF: Double = 0.0,

    @SerializedName("DKK")
    var USD_DKK: Double = 0.0,

    @SerializedName("DOP")
    var USD_DOP: Double = 0.0,

    @SerializedName("DZD")
    var USD_DZD: Double = 0.0,

    @SerializedName("EGP")
    var USD_EGP: Double = 0.0,

    @SerializedName("ERN")
    var USD_ERN: Double = 0.0,

    @SerializedName("ETB")
    var USD_ETB: Double = 0.0,

    @SerializedName("EUR")
    var USD_EUR: Double = 0.0,

    @SerializedName("FJD")
    var USD_FJD: Double = 0.0,

    @SerializedName("FKP")
    var USD_FKP: Double = 0.0,

    @SerializedName("GBP")
    var USD_GBP: Double = 0.0,

    @SerializedName("GEL")
    var USD_GEL: Double = 0.0,

    @SerializedName("GGP")
    var USD_GGP: Double = 0.0,

    @SerializedName("GHS")
    var USD_GHS: Double = 0.0,

    @SerializedName("GIP")
    var USD_GIP: Double = 0.0,

    @SerializedName("GMD")
    var USD_GMD: Double = 0.0,

    @SerializedName("GNF")
    var USD_GNF: Double = 0.0,

    @SerializedName("GTQ")
    var USD_GTQ: Double = 0.0,

    @SerializedName("GYD")
    var USD_GYD: Double = 0.0,

    @SerializedName("HKD")
    var USD_HKD: Double = 0.0,

    @SerializedName("HNL")
    var USD_HNL: Double = 0.0,

    @SerializedName("HRK")
    var USD_HRK: Double = 0.0,

    @SerializedName("HTG")
    var USD_HTG: Double = 0.0,

    @SerializedName("HUF")
    var USD_HUF: Double = 0.0,

    @SerializedName("IDR")
    var USD_IDR: Double = 0.0,

    @SerializedName("ILS")
    var USD_ILS: Double = 0.0,

    @SerializedName("IMP")
    var USD_IMP: Double = 0.0,

    @SerializedName("INR")
    var USD_INR: Double = 0.0,

    @SerializedName("IQD")
    var USD_IQD: Double = 0.0,

    @SerializedName("IRR")
    var USD_IRR: Double = 0.0,

    @SerializedName("ISK")
    var USD_ISK: Double = 0.0,

    @SerializedName("JEP")
    var USD_JEP: Double = 0.0,

    @SerializedName("JMD")
    var USD_JMD: Double = 0.0,

    @SerializedName("JOD")
    var USD_JOD: Double = 0.0,

    @SerializedName("JPY")
    var USD_JPY: Double = 0.0,

    @SerializedName("KES")
    var USD_KES: Double = 0.0,

    @SerializedName("KGS")
    var USD_KGS: Double = 0.0,

    @SerializedName("KHR")
    var USD_KHR: Double = 0.0,

    @SerializedName("KMF")
    var USD_KMF: Double = 0.0,

    @SerializedName("KPW")
    var USD_KPW: Double = 0.0,

    @SerializedName("KRW")
    var USD_KRW: Double = 0.0,

    @SerializedName("KWD")
    var USD_KWD: Double = 0.0,

    @SerializedName("KYD")
    var USD_KYD: Double = 0.0,

    @SerializedName("KZT")
    var USD_KZT: Double = 0.0,

    @SerializedName("LAK")
    var USD_LAK: Double = 0.0,

    @SerializedName("LBP")
    var USD_LBP: Double = 0.0,

    @SerializedName("LKR")
    var USD_LKR: Double = 0.0,

    @SerializedName("LRD")
    var USD_LRD: Double = 0.0,

    @SerializedName("LSL")
    var USD_LSL: Double = 0.0,

    @SerializedName("LYD")
    var USD_LYD: Double = 0.0,

    @SerializedName("MAD")
    var USD_MAD: Double = 0.0,

    @SerializedName("MDL")
    var USD_MDL: Double = 0.0,

    @SerializedName("MGA")
    var USD_MGA: Double = 0.0,

    @SerializedName("MKD")
    var USD_MKD: Double = 0.0,

    @SerializedName("MMK")
    var USD_MMK: Double = 0.0,

    @SerializedName("MNT")
    var USD_MNT: Double = 0.0,

    @SerializedName("MOP")
    var USD_MOP: Double = 0.0,

    @SerializedName("MRO")
    var USD_MRO: Double = 0.0,

    @SerializedName("MUR")
    var USD_MUR: Double = 0.0,

    @SerializedName("MVR")
    var USD_MVR: Double = 0.0,

    @SerializedName("MWK")
    var USD_MWK: Double = 0.0,

    @SerializedName("MXN")
    var USD_MXN: Double = 0.0,

    @SerializedName("MYR")
    var USD_MYR: Double = 0.0,

    @SerializedName("MZN")
    var USD_MZN: Double = 0.0,

    @SerializedName("NAD")
    var USD_NAD: Double = 0.0,

    @SerializedName("NGN")
    var USD_NGN: Double = 0.0,

    @SerializedName("NIO")
    var USD_NIO: Double = 0.0,

    @SerializedName("NOK")
    var USD_NOK: Double = 0.0,

    @SerializedName("NPR")
    var USD_NPR: Double = 0.0,

    @SerializedName("NZD")
    var USD_NZD: Double = 0.0,

    @SerializedName("OMR")
    var USD_OMR: Double = 0.0,

    @SerializedName("PAB")
    var USD_PAB: Double = 0.0,

    @SerializedName("PEN")
    var USD_PEN: Double = 0.0,

    @SerializedName("PGK")
    var USD_PGK: Double = 0.0,

    @SerializedName("PHP")
    var USD_PHP: Double = 0.0,

    @SerializedName("PKR")
    var USD_PKR: Double = 0.0,

    @SerializedName("PLN")
    var USD_PLN: Double = 0.0,

    @SerializedName("PYG")
    var USD_PYG: Double = 0.0,

    @SerializedName("QAR")
    var USD_QAR: Double = 0.0,

    @SerializedName("RON")
    var USD_RON: Double = 0.0,

    @SerializedName("RSD")
    var USD_RSD: Double = 0.0,

    @SerializedName("RUB")
    var USD_RUB: Double = 0.0,

    @SerializedName("RWF")
    var USD_RWF: Double = 0.0,

    @SerializedName("SAR")
    var USD_SAR: Double = 0.0,

    @SerializedName("SBD")
    var USD_SBD: Double = 0.0,

    @SerializedName("SCR")
    var USD_SCR: Double = 0.0,

    @SerializedName("SDG")
    var USD_SDG: Double = 0.0,

    @SerializedName("SEK")
    var USD_SEK: Double = 0.0,

    @SerializedName("SGD")
    var USD_SGD: Double = 0.0,

    @SerializedName("SHP")
    var USD_SHP: Double = 0.0,

    @SerializedName("SLL")
    var USD_SLL: Double = 0.0,

    @SerializedName("SOS")
    var USD_SOS: Double = 0.0,

    @SerializedName("SRD")
    var USD_SRD: Double = 0.0,

    @SerializedName("SSP")
    var USD_SSP: Double = 0.0,

    @SerializedName("STN")
    var USD_STN: Double = 0.0,

    @SerializedName("SVC")
    var USD_SVC: Double = 0.0,

    @SerializedName("SYP")
    var USD_SYP: Double = 0.0,

    @SerializedName("SZL")
    var USD_SZL: Double = 0.0,

    @SerializedName("THB")
    var USD_THB: Double = 0.0,

    @SerializedName("TJS")
    var USD_TJS: Double = 0.0,

    @SerializedName("TMT")
    var USD_TMT: Double = 0.0,

    @SerializedName("TND")
    var USD_TND: Double = 0.0,

    @SerializedName("TOP")
    var USD_TOP: Double = 0.0,

    @SerializedName("TRY")
    var USD_TRY: Double = 0.0,

    @SerializedName("TTD")
    var USD_TTD: Double = 0.0,

    @SerializedName("TWD")
    var USD_TWD: Double = 0.0,

    @SerializedName("TZS")
    var USD_TZS: Double = 0.0,

    @SerializedName("UAH")
    var USD_UAH: Double = 0.0,

    @SerializedName("UGX")
    var USD_UGX: Double = 0.0,

    @SerializedName("USD")
    var USD_USD: Double = 0.0,

    @SerializedName("UYU")
    var USD_UYU: Double = 0.0,

    @SerializedName("UZS")
    var USD_UZS: Double = 0.0,

    @SerializedName("VEF")
    var USD_VEF: Double = 0.0,

    @SerializedName("VND")
    var USD_VND: Double = 0.0,

    @SerializedName("VUV")
    var USD_VUV: Double = 0.0,

    @SerializedName("WST")
    var USD_WST: Double = 0.0,

    @SerializedName("XAF")
    var USD_XAF: Double = 0.0,

    @SerializedName("XAG")
    var USD_XAG: Double = 0.0,

    @SerializedName("XAU")
    var USD_XAU: Double = 0.0,

    @SerializedName("XCD")
    var USD_XCD: Double = 0.0,

    @SerializedName("XOF")
    var USD_XOF: Double = 0.0,

    @SerializedName("XPF")
    var USD_XPF: Double = 0.0,

    @SerializedName("YER")
    var USD_YER: Double = 0.0,

    @SerializedName("ZAR")
    var USD_ZAR: Double = 0.0,

    @SerializedName("ZMW")
    var USD_ZMW: Double = 0.0,

    @SerializedName("ZWL")
    var USD_ZWL: Double = 0.0,
)*/
