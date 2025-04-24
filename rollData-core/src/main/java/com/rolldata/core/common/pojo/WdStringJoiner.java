package com.rolldata.core.common.pojo;

import java.util.Objects;

/**
 * 在{@link java.util.StringJoiner}基础上加清空操作
 * <p>示例：WdStringJoiner sb = new WdStringJoiner(", ", "[", "]");
 * <p>sb.add(元素1).add(元素2);
 * <p>结果：[元素1,元素2]
 *
 * @Title: WdStringJoiner
 * @Description: WdStringJoiner
 * @Company: www.wrenchdata.com
 * @author: shenshilong
 * @date: 2021-01-19
 * @version: V1.0
 */
public final class WdStringJoiner {

    private final String prefix;
    private final String delimiter;
    private final String suffix;

    /*
     * StringBuilder value -- at any time, the characters constructed from the
     * prefix, the added element separated by the delimiter, but without the
     * suffix, so that we can more easily add elements without having to jigger
     * the suffix each time.
     */
    private StringBuilder value;

    /*
     * By default, the string consisting of prefix+suffix, returned by
     * toString(), or properties of value, when no elements have yet been added,
     * i.e. when it is empty.  This may be overridden by the user to be some
     * other value including the empty String.
     */
    private String emptyValue;

    /**
     * Constructs a {@code WdStringJoiner} with no characters in it, with no
     * {@code prefix} or {@code suffix}, and a copy of the supplied
     * {@code delimiter}.
     * If no characters are added to the {@code WdStringJoiner} and methods
     * accessing the value of it are invoked, it will not return a
     * {@code prefix} or {@code suffix} (or properties thereof) in the result,
     * unless {@code setEmptyValue} has first been called.
     *
     * @param  delimiter the sequence of characters to be used between each
     *         element added to the {@code WdStringJoiner} value
     * @throws NullPointerException if {@code delimiter} is {@code null}
     */
    public WdStringJoiner(CharSequence delimiter) {
        this(delimiter, "", "");
    }

    /**
     * Constructs a {@code WdStringJoiner} with no characters in it using copies
     * of the supplied {@code prefix}, {@code delimiter} and {@code suffix}.
     * If no characters are added to the {@code WdStringJoiner} and methods
     * accessing the string value of it are invoked, it will return the
     * {@code prefix + suffix} (or properties thereof) in the result, unless
     * {@code setEmptyValue} has first been called.
     *
     * @param  delimiter the sequence of characters to be used between each
     *         element added to the {@code WdStringJoiner}
     * @param  prefix the sequence of characters to be used at the beginning
     * @param  suffix the sequence of characters to be used at the end
     * @throws NullPointerException if {@code prefix}, {@code delimiter}, or
     *         {@code suffix} is {@code null}
     */
    public WdStringJoiner(CharSequence delimiter,
        CharSequence prefix,
        CharSequence suffix) {
        Objects.requireNonNull(prefix, "The prefix must not be null");
        Objects.requireNonNull(delimiter, "The delimiter must not be null");
        Objects.requireNonNull(suffix, "The suffix must not be null");
        // make defensive copies of arguments
        this.prefix = prefix.toString();
        this.delimiter = delimiter.toString();
        this.suffix = suffix.toString();
        this.emptyValue = this.prefix + this.suffix;
    }


    /**
     * Sets the sequence of characters to be used when determining the string
     * representation of this {@code WdStringJoiner} and no elements have been
     * added yet, that is, when it is empty.  A copy of the {@code emptyValue}
     * parameter is made for this purpose. Note that once an add method has been
     * called, the {@code WdStringJoiner} is no longer considered empty, even if
     * the element(s) added correspond to the empty {@code String}.
     *
     * @param  emptyValue the characters to return as the value of an empty
     *         {@code WdStringJoiner}
     * @return this {@code WdStringJoiner} itself so the calls may be chained
     * @throws NullPointerException when the {@code emptyValue} parameter is
     *         {@code null}
     */
    public WdStringJoiner setEmptyValue(CharSequence emptyValue) {
        this.emptyValue = Objects.requireNonNull(emptyValue,
                                                 "The empty value must not be null").toString();
        return this;
    }

    /**
     * Returns the current value, consisting of the {@code prefix}, the values
     * added so far separated by the {@code delimiter}, and the {@code suffix},
     * unless no elements have been added in which case, the
     * {@code prefix + suffix} or the {@code emptyValue} characters are returned
     *
     * @return the string representation of this {@code WdStringJoiner}
     */
    @Override
    public String toString() {
        if (value == null || value.length() == 0) {
            return emptyValue;
        } else {
            if (suffix.equals("")) {
                return value.toString();
            } else {
                int initialLength = value.length();
                String result = value.append(suffix).toString();
                // reset value to pre-append initialLength
                value.setLength(initialLength);
                return result;
            }
        }
    }

    /**
     * Adds a copy of the given {@code CharSequence} value as the next
     * element of the {@code WdStringJoiner} value. If {@code newElement} is
     * {@code null}, then {@code "null"} is added.
     *
     * @param  newElement The element to add
     * @return a reference to this {@code WdStringJoiner}
     */
    public WdStringJoiner add(CharSequence newElement) {
        prepareBuilder().append(newElement);
        return this;
    }

    /**
     * Adds a copy of the given {@code Object} value as the next
     * element of the {@code WdStringJoiner} value. will not add prefix. If {@code newElement} is
     * {@code null}, then {@code "null"} is added.<br>
     * Added by: ShenShiLong<br>
     * 使用此方法前,应执行add(),否则添加个空串
     *
     * @param  newElement The element to add
     * @return a reference to this {@code WdStringJoiner}
     */
    public WdStringJoiner append(Object newElement) {
        if (null == value) {
            add("");
        }
        value.append(newElement);
        return this;
    }

    /**
     * Adds the contents of the given {@code WdStringJoiner} without prefix and
     * suffix as the next element if it is non-empty. If the given {@code
     * StringJoiner} is empty, the call has no effect.
     *
     * <p>A {@code WdStringJoiner} is empty if {@link #add(CharSequence) add()}
     * has never been called, and if {@code merge()} has never been called
     * with a non-empty {@code WdStringJoiner} argument.
     *
     * <p>If the other {@code WdStringJoiner} is using a different delimiter,
     * then elements from the other {@code WdStringJoiner} are concatenated with
     * that delimiter and the result is appended to this {@code WdStringJoiner}
     * as a single element.
     *
     * @param other The {@code WdStringJoiner} whose contents should be merged
     *              into this one
     * @throws NullPointerException if the other {@code WdStringJoiner} is null
     * @return This {@code WdStringJoiner}
     */
    public WdStringJoiner merge(WdStringJoiner other) {
        Objects.requireNonNull(other);
        if (other.value != null) {
            final int length = other.value.length();
            // lock the length so that we can seize the data to be appended
            // before initiate copying to avoid interference, especially when
            // merge 'this'
            StringBuilder builder = prepareBuilder();
            builder.append(other.value, other.prefix.length(), length);
        }
        return this;
    }

    private StringBuilder prepareBuilder() {
        if (value != null) {
            if (value.length() > 0) {
                value.append(delimiter);
            }
        } else {
            value = new StringBuilder().append(prefix);
        }
        return value;
    }

    /**
     * Returns the length of the {@code String} representation
     * of this {@code WdStringJoiner}. Note that if
     * no add methods have been called, then the length of the {@code String}
     * representation (either {@code prefix + suffix} or {@code emptyValue})
     * will be returned. The value should be equivalent to
     * {@code toString().length()}.
     *
     * @return the length of the current value of {@code WdStringJoiner}
     */
    public int length() {
        // Remember that we never actually append the suffix unless we return
        // the full (present) value or some sub-string or length of it, so that
        // we can add on more if we need to.
        return (value != null ? value.length() + suffix.length() :
            emptyValue.length());
    }

    public void setLength(int newLength) {
        this.value.setLength(newLength);
    }

    public void clearValue() {
        this.value.setLength(0);
    }
}
