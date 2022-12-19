package com.spravochnic.scbguide.utils

import android.annotation.SuppressLint
import android.text.TextUtils
import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import javax.xml.transform.OutputKeys
import javax.xml.transform.Source
import javax.xml.transform.TransformerException
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource

object LogUtil {
    const val TYPE_TEXT = 0
    const val TYPE_JSON = 1
    const val TYPE_XML = 2
    private const val JSON_INDENT = 4
    private const val XML_INDENT = "4"
    private const val LINE_TOP_LEFT =
        "┏┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅"
    private const val LINE_CENTER_LEFT =
        "┣┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅"
    private const val LINE_BOTTOM_LEFT =
        "┗┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅"
    private const val LINE_VERTICAL_DASH = "┇"
    private val LINE_SEPARATOR = System.getProperty("line.separator")
    private val CLASSNAME = LogUtil::class.java.name
    private const val LOCAL_LOG_FILE_PREFIX = "LogUtil_"
    private const val sLogFileDirPath = ""
    private const val sLogFileName = ""
    private const val sLogFilePath = ""
    private var sDefaultTag = "Ultra"
    private const val showLog = true // enable logging
    private const val showMethodInfo = true // show method info
    private const val showThreadName = true // show thread info
    private const val showDateTime = true // show log info
    private const val showWithFormat = false // enable table format

    // 01.07.2020 added split line support
    private const val splitLines = true
    private const val MAX_SPLIT_LENGTH = 2048

    @SuppressLint("SimpleDateFormat")
    private val logFullDateFormat =
        SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")

    @SuppressLint("SimpleDateFormat")
    private val logSaveDateFormat =
        SimpleDateFormat("yyyy-MM-dd")

    /**
     * Get class simple name by object
     *
     * @param object object
     * @return object class simple name
     */
    private fun getClassSimpleName(`object`: Any?): String {
        return `object`?.javaClass?.simpleName ?: ""
    }

    /**
     * Get stack trace offset where our code reaches
     *
     * @param stackTraceElements array of StackTraceElement
     * @return offset index; -1 if no related
     */
    private fun getStackTraceOffset(stackTraceElements: Array<StackTraceElement>): Int {
        var findLogUtil = true
        for (i in stackTraceElements.indices) {
            val e = stackTraceElements[i]
            val name = e.className
            if (findLogUtil) {
                if (name == CLASSNAME) {
                    //reach LogUtil stack
                    findLogUtil = false
                }
            } else {
                if (name != CLASSNAME) {
                    //reach real code
                    return i
                }
            }
        }
        return -1
    }

    /**
     * Get array of StackTraceElements
     *
     * @return array of StackTraceElements
     */
    private val stackTraceElement: StackTraceElement
        get() {
            val stackTraceElements =
                Thread.currentThread().stackTrace
            val stackTraceOffset =
                getStackTraceOffset(
                    stackTraceElements
                )
            return stackTraceElements[stackTraceOffset]
        }

    /**
     * Get line number of stackTraceElement
     *
     * @param stackTraceElement stackTraceElement
     * @return line number of stackTraceElement, e.g. "123"
     */
    private fun getLineNumber(stackTraceElement: StackTraceElement): Int {
        return stackTraceElement.lineNumber
    }

    /**
     * Get full class name of stackTraceElement
     *
     * @param stackTraceElement stackTraceElement
     * @return full class name of stackTraceElement, e.g. "com.packagename.classame"
     */
    private fun getClassName(stackTraceElement: StackTraceElement): String {
        return stackTraceElement.className
    }

    private fun getSingleClassName(stackTraceElement: StackTraceElement): String {
        return getFileName(
            stackTraceElement
        ).replace(".java", "")
    }

    /**
     * Get method name of stackTraceElement
     *
     * @param stackTraceElement stackTraceElement
     * @return method name without quote of stackTraceElement, e.g. "method"
     */
    private fun getMethodName(stackTraceElement: StackTraceElement): String {
        return stackTraceElement.methodName
    }

    /**
     * Get file name of stackTraceElement
     *
     * @param stackTraceElement stackTraceElement
     * @return file name of stackTraceElement, e.g. "xxx.java"
     */
    private fun getFileName(stackTraceElement: StackTraceElement): String {
        return stackTraceElement.fileName
    }

    private val dateTime: String
        get() = logFullDateFormat.format(Date())

    /**
     * Generate log message with grid
     *
     * @param msg msg
     * @return log message with grid
     */
    private fun generateGridMsg(msg: String?): String {
        return if (showWithFormat) {
            (LINE_TOP_LEFT + LINE_SEPARATOR
                    + LINE_VERTICAL_DASH + clickableLineNumber + LINE_SEPARATOR
                    + LINE_CENTER_LEFT + LINE_SEPARATOR
                    + addLineAhead(msg)
                    + LINE_BOTTOM_LEFT)
        } else {
            clickableLineNumber + LINE_SEPARATOR + msg
        }
    }

    /**
     * Combine infos to generate a log-clickable string
     *
     * @return log-clickable string, e.g. "com.packagename.class.method(filename.java)"
     */
    private val clickableLineNumber: String
        private get() {
            val stackTraceElement =
                stackTraceElement
            val fileName = getFileName(
                stackTraceElement
            )
            val className =
                getSingleClassName(
                    stackTraceElement
                )
            val lineNumber =
                getLineNumber(
                    stackTraceElement
                )
            val methodName =
                getMethodName(
                    stackTraceElement
                )
            val threadName = Thread.currentThread().name
            val dateTime = dateTime
            var result = ""
            if (showMethodInfo) {
                result += "Method: $className.$methodName ($fileName:$lineNumber) $LINE_VERTICAL_DASH "
            }
            if (showThreadName) {
                result += "Thread: $threadName $LINE_VERTICAL_DASH "
            }
            if (showDateTime) {
                result += "Time: $dateTime $LINE_VERTICAL_DASH "
            }
            return result
        }

    /**
     * Add a vertical line ahead of each line of string
     *
     * @param string string
     * @return string with vertical line ahead of each line
     */
    private fun addLineAhead(string: String?): String {
        var result = ""
        val splitStrings =
            string!!.split(LINE_SEPARATOR!!.toRegex()).toTypedArray()
        for (splitString in splitStrings) {
            result += LINE_VERTICAL_DASH + splitString + LINE_SEPARATOR
        }
        return result
    }

    /**
     * Save string to local dir
     *
     * @param logFileDirPath local log file dir path
     * @param msg            msg
     */
    private fun saveLogLocally(logFileDirPath: String, msg: String?) {
        val logFileName =
            LOCAL_LOG_FILE_PREFIX + logSaveDateFormat.format(Date())
        val logFile = createLocalFile(
            logFileDirPath,
            logFileName
        )
        append(
            logFile,
            generateGridMsg(msg) + LINE_SEPARATOR
        )
    }

    /**
     * Create a local file
     *
     * @param dirPath  dir where file in
     * @param fileName file name
     * @return file
     */
    private fun createLocalFile(dirPath: String, fileName: String): File? {
        val dir = File(dirPath)
        if (!dir.exists()) {
            if (!dir.mkdir()) {
                //fail to mkdir
                e("Fail to create local directory: $dirPath")
                return null
            }
        }
        val filePath = "$dirPath/$fileName"
        val file = File(filePath)
        try {
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    //fail to create file
                    e("Fail to create local file: $filePath")
                    return null
                }
            } else {
                //file already exists
                return file
            }
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        return file
    }

    /**
     * Append string to logFile
     *
     * @param logFile log file
     * @param text    string to be appended
     */
    private fun append(logFile: File?, text: String) {
        var bufferedWriter: BufferedWriter? = null
        try {
            bufferedWriter = BufferedWriter(FileWriter(logFile, true))
            bufferedWriter.append(text)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    /**
     * Set log tag
     *
     * @param tag log tag
     */
    fun setTag(tag: String) {
        sDefaultTag = tag
    }

    /**
     * print a VERBOSE log
     *
     * @param tag tag
     * @param msg Message to be shown
     */
    fun v(tag: String?, msg: String?) {
        if (showLog && msg != null) {
            val formattedMessage =
                generateGridMsg(msg)
            if (splitLines) {
                var i = 0
                while (i < formattedMessage.length) {
                    Log.v(
                        tag,
                        formattedMessage.substring(
                            i,
                            Math.min(
                                formattedMessage.length,
                                i + MAX_SPLIT_LENGTH
                            )
                        )
                    )
                    i += MAX_SPLIT_LENGTH
                }
            } else {
                Log.v(tag, formattedMessage)
            }
        }
    }

    /**
     * print a VERBOSE log
     *
     * @param obj Object that use its class name to be as a tag
     * @param msg Message to be shown
     */
    fun v(obj: Any?, msg: String?) {
        if (showLog && msg != null) {
            v(
                getClassSimpleName(
                    obj
                ), msg
            )
        }
    }

    /**
     * print a VERBOSE log using default tag
     *
     * @param msg msg
     */
    fun v(msg: String?) {
        if (showLog && msg != null) {
            v(
                sDefaultTag,
                msg
            )
        }
    }

    /**
     * print a VERBOSE log with different type of format
     *
     * @param type TYPE_TEXT, TYPE_JSON, TYPE_XML
     * @param msg  msg
     */
    fun v(type: Int, msg: String) {
        when (type) {
            TYPE_JSON -> v(
                formatJson(msg)
            )
            TYPE_XML -> v(
                formatXml(msg)
            )
            TYPE_TEXT -> v(
                msg
            )
            else -> v(msg)
        }
    }

    /**
     * print an VERBOSE log, with option to save log locally
     *
     * @param saveLocally true if save locally; Otherwise false
     * @param msg         msg
     */
    fun v(saveLocally: Boolean, msg: String?) {
        if (showLog && msg != null) {
            v(
                sDefaultTag,
                msg
            )
        }
        if (saveLocally) {
            saveLogLocally(
                sLogFileDirPath,
                msg
            )
        }
    }

    /**
     * print a DEBUG log
     *
     * @param tag tag
     * @param msg Message to be shown
     */
    fun d(tag: String, msg: String?) {
        if (showLog && msg != null) {
            val formattedMessage =
                generateGridMsg(msg)
            if (splitLines) {
                var i = 0
                while (i < formattedMessage.length) {
                    Log.d(
                        tag,
                        formattedMessage.substring(
                            i,
                            Math.min(
                                formattedMessage.length,
                                i + MAX_SPLIT_LENGTH
                            )
                        )
                    )
                    i += MAX_SPLIT_LENGTH
                }
            } else {
                Log.d(tag, formattedMessage)
            }
        }
    }

    /**
     * print a DEBUG log
     *
     * @param obj Object that use its class name to be as a tag
     * @param msg Message to be shown
     */
    fun d(obj: Any, msg: String?) {
        if (showLog && msg != null) {
            d(
                obj.javaClass.simpleName,
                msg
            )
        }
    }

    /**
     * print a DEBUG log using default tag
     *
     * @param msg msg
     */
    fun d(msg: String?) {
        if (showLog && msg != null) {
            d(
                sDefaultTag,
                msg
            )
        }
    }

    /**
     * print a DEBUG log with different type of format
     *
     * @param type TYPE_TEXT, TYPE_JSON, TYPE_XML
     * @param msg  msg
     */
    fun d(type: Int, msg: String) {
        when (type) {
            TYPE_JSON -> d(
                formatJson(msg)
            )
            TYPE_XML -> d(
                formatXml(msg)
            )
            TYPE_TEXT -> d(
                msg
            )
            else -> d(msg)
        }
    }

    /**
     * print an DEBUG log, with option to save log locally
     *
     * @param saveLocally true if save locally; Otherwise false
     * @param msg         msg
     */
    fun d(saveLocally: Boolean, msg: String?) {
        if (showLog && msg != null) {
            d(
                sDefaultTag,
                msg
            )
        }
        if (saveLocally) {
            saveLogLocally(
                sLogFileDirPath,
                msg
            )
        }
    }

    /**
     * print an INFO log
     *
     * @param tag tag
     * @param msg Message to be shown
     */
    fun i(tag: String, msg: String?) {
        if (showLog && msg != null) {
            val formattedMessage =
                generateGridMsg(msg)
            if (splitLines) {
                var i = 0
                while (i < formattedMessage.length) {
                    Log.i(
                        tag,
                        formattedMessage.substring(
                            i,
                            Math.min(
                                formattedMessage.length,
                                i + MAX_SPLIT_LENGTH
                            )
                        )
                    )
                    i += MAX_SPLIT_LENGTH
                }
            } else {
                Log.i(tag, formattedMessage)
            }
        }
    }

    /**
     * print an INFO log
     *
     * @param obj Object that use its class name to be as a tag
     * @param msg Message to be shown
     */
    fun i(obj: Any, msg: String?) {
        if (showLog && msg != null) {
            i(
                obj.javaClass.simpleName,
                msg
            )
        }
    }

    /**
     * print an INFO log using default tag
     *
     * @param msg msg
     */
    fun i(msg: String?) {
        if (showLog && msg != null) {
            i(
                sDefaultTag,
                msg
            )
        }
    }

    /**
     * print an INFO log with different type of format
     *
     * @param type TYPE_TEXT, TYPE_JSON, TYPE_XML
     * @param msg  msg
     */
    fun i(type: Int, msg: String) {
        when (type) {
            TYPE_JSON -> i(
                formatJson(msg)
            )
            TYPE_XML -> i(
                formatXml(msg)
            )
            TYPE_TEXT -> i(
                msg
            )
            else -> i(msg)
        }
    }

    /**
     * print an INFO log, with option to save log locally
     *
     * @param saveLocally true if save locally; Otherwise false
     * @param msg         msg
     */
    fun i(saveLocally: Boolean, msg: String?) {
        if (showLog && msg != null) {
            i(
                sDefaultTag,
                msg
            )
        }
        if (saveLocally) {
            saveLogLocally(
                sLogFileDirPath,
                msg
            )
        }
    }

    /**
     * print a WARN log
     *
     * @param tag tag
     * @param msg Message to be shown
     */
    fun w(tag: String, msg: String?) {
        if (showLog && msg != null) {
            val formattedMessage =
                generateGridMsg(msg)
            if (splitLines) {
                var i = 0
                while (i < formattedMessage.length) {
                    Log.w(
                        tag,
                        formattedMessage.substring(
                            i,
                            Math.min(
                                formattedMessage.length,
                                i + MAX_SPLIT_LENGTH
                            )
                        )
                    )
                    i += MAX_SPLIT_LENGTH
                }
            } else {
                Log.w(tag, formattedMessage)
            }
        }
    }

    /**
     * print a WARN log
     *
     * @param obj Object that use its class name to be as a tag
     * @param msg Message to be shown
     */
    fun w(obj: Any, msg: String?) {
        if (showLog && msg != null) {
            w(
                obj.javaClass.simpleName,
                msg
            )
        }
    }

    /**
     * print a WARN log using default tag
     *
     * @param msg msg
     */
    fun w(msg: String?) {
        if (showLog && msg != null) {
            w(
                sDefaultTag,
                msg
            )
        }
    }

    /**
     * print a WARN log with different type of format
     *
     * @param type TYPE_TEXT, TYPE_JSON, TYPE_XML
     * @param msg  msg
     */
    fun w(type: Int, msg: String) {
        when (type) {
            TYPE_JSON -> w(
                formatJson(msg)
            )
            TYPE_XML -> w(
                formatXml(msg)
            )
            TYPE_TEXT -> w(
                msg
            )
            else -> w(msg)
        }
    }

    /**
     * print an WARN log, with option to save log locally
     *
     * @param saveLocally true if save locally; Otherwise false
     * @param msg         msg
     */
    fun w(saveLocally: Boolean, msg: String?) {
        if (showLog && msg != null) {
            w(
                sDefaultTag,
                msg
            )
        }
        if (saveLocally) {
            saveLogLocally(
                sLogFileDirPath,
                msg
            )
        }
    }

    /**
     * print an ERROR log
     *
     * @param tag tag
     * @param msg Message to be shown
     */
    fun e(tag: String, msg: String?) {
        if (showLog && msg != null) {
            val formattedMessage =
                generateGridMsg(msg)
            if (splitLines) {
                var i = 0
                while (i < formattedMessage.length) {
                    Log.e(
                        tag,
                        formattedMessage.substring(
                            i,
                            Math.min(
                                formattedMessage.length,
                                i + MAX_SPLIT_LENGTH
                            )
                        )
                    )
                    i += MAX_SPLIT_LENGTH
                }
            } else {
                Log.e(tag, formattedMessage)
            }
        }
    }

    /**
     * print an ERROR log
     *
     * @param obj Object that use its class name to be as a tag
     * @param msg Message to be shown
     */
    fun e(obj: Any, msg: String?) {
        if (showLog && msg != null) {
            e(
                obj.javaClass.simpleName,
                msg
            )
        }
    }

    /**
     * print a ERROR log using default tag
     *
     * @param msg msg
     */
    fun e(msg: String?) {
        if (showLog && msg != null) {
            e(
                sDefaultTag,
                msg
            )
        }
    }

    /**
     * print a ERROR log with different type of format
     *
     * @param type TYPE_TEXT, TYPE_JSON, TYPE_XML
     * @param msg  msg
     */
    fun e(type: Int, msg: String) {
        when (type) {
            TYPE_JSON -> e(
                formatJson(msg)
            )
            TYPE_XML -> e(
                formatXml(msg)
            )
            TYPE_TEXT -> e(
                msg
            )
            else -> e(msg)
        }
    }

    /**
     * print an ERROR log, with option to save log locally
     *
     * @param saveLocally true if save locally; Otherwise false
     * @param msg         msg
     */
    fun e(saveLocally: Boolean, msg: String?) {
        if (showLog && msg != null) {
            e(
                sDefaultTag,
                msg
            )
        }
        if (saveLocally) {
            saveLogLocally(
                sLogFileDirPath,
                msg
            )
        }
    }

    /**
     * Format Json to a human readable string
     *
     * @param jsonStr json string
     * @return human readable string
     */
    fun formatJson(jsonStr: String): String {
        var json = jsonStr
        return if (TextUtils.isEmpty(json)) {
            "Empty/Null json content"
        } else try {
            json = json.trim { it <= ' ' }
            if (json.startsWith("{")) {
                val jsonObject = JSONObject(json)
                jsonObject.toString(JSON_INDENT)
            } else if (json.startsWith("[")) {
                val jsonArray = JSONArray(json)
                jsonArray.toString(JSON_INDENT)
            } else {
                "Invalid Json: $json"
            }
        } catch (e: JSONException) {
            "Invalid Json: $json"
        }
    }

    /**
     * Format xml to a human readable string
     *
     * @param xml xml string
     * @return human readable string
     */
    fun formatXml(xml: String): String {
        return if (TextUtils.isEmpty(xml)) {
            "Empty/Null xml content"
        } else try {
            val xmlInput: Source =
                StreamSource(StringReader(xml))
            val xmlOutput =
                StreamResult(StringWriter())
            val transformer =
                TransformerFactory.newInstance().newTransformer()
            transformer.setOutputProperty(OutputKeys.INDENT, "yes")
            transformer.setOutputProperty(
                "{http://xml.apache.org/xslt}indent-amount",
                XML_INDENT
            )
            transformer.transform(xmlInput, xmlOutput)
            xmlOutput.writer.toString().replaceFirst(">".toRegex(), ">\n")
        } catch (e: TransformerException) {
            "Invalid xml"
        }
    }
}