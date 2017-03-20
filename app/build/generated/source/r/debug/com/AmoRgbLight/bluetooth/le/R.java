/* AUTO-GENERATED FILE.  DO NOT MODIFY.
 *
 * This class was automatically generated by the
 * aapt tool from the resource data it found.  It
 * should not be modified by hand.
 */

package com.AmoRgbLight.bluetooth.le;

public final class R {
    public static final class attr {
        /** <p>Must be a color value, in the form of "<code>#<i>rgb</i></code>", "<code>#<i>argb</i></code>",
"<code>#<i>rrggbb</i></code>", or "<code>#<i>aarrggbb</i></code>".
<p>This may also be a reference to a resource (in the form
"<code>@[<i>package</i>:]<i>type</i>:<i>name</i></code>") or
theme attribute (in the form
"<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>")
containing a value of this type.
         */
        public static final int center_color=0x7f010002;
        /** <p>Must be a dimension value, which is a floating point number appended with a unit such as "<code>14.5sp</code>".
Available units are: px (pixels), dp (density-independent pixels), sp (scaled pixels based on preferred font size),
in (inches), mm (millimeters).
<p>This may also be a reference to a resource (in the form
"<code>@[<i>package</i>:]<i>type</i>:<i>name</i></code>") or
theme attribute (in the form
"<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>")
containing a value of this type.
         */
        public static final int center_radius=0x7f010001;
        /** <p>Must be a dimension value, which is a floating point number appended with a unit such as "<code>14.5sp</code>".
Available units are: px (pixels), dp (density-independent pixels), sp (scaled pixels based on preferred font size),
in (inches), mm (millimeters).
<p>This may also be a reference to a resource (in the form
"<code>@[<i>package</i>:]<i>type</i>:<i>name</i></code>") or
theme attribute (in the form
"<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>")
containing a value of this type.
         */
        public static final int circle_radius=0x7f010000;
    }
    public static final class id {
        public static final int connection_state=0x7f05000b;
        public static final int data_value=0x7f05000c;
        public static final int device_address=0x7f05000a;
        public static final int device_beacon_uuid=0x7f05000f;
        public static final int device_major_minor=0x7f050011;
        public static final int device_name=0x7f05000e;
        public static final int device_txPower_rssi=0x7f050010;
        public static final int gatt_services_list=0x7f05000d;
        public static final int linearLayout=0x7f050000;
        public static final int menu_connect=0x7f050013;
        public static final int menu_disconnect=0x7f050014;
        public static final int menu_refresh=0x7f050012;
        public static final int menu_scan=0x7f050015;
        public static final int menu_stop=0x7f050016;
        public static final int seekbar_blue=0x7f050009;
        public static final int seekbar_brightness=0x7f050003;
        public static final int seekbar_green=0x7f050007;
        public static final int seekbar_red=0x7f050005;
        public static final int togglebutton_onoff=0x7f050001;
        public static final int txt_brightness=0x7f050002;
        public static final int txt_color_blue=0x7f050008;
        public static final int txt_color_green=0x7f050006;
        public static final int txt_color_red=0x7f050004;
    }
    public static final class layout {
        public static final int actionbar_indeterminate_progress=0x7f020000;
        public static final int amo_rgb_activity=0x7f020001;
        public static final int gatt_services_characteristics=0x7f020002;
        public static final int listitem_device=0x7f020003;
    }
    public static final class menu {
        public static final int gatt_services=0x7f040000;
        public static final int main=0x7f040001;
    }
    public static final class string {
        public static final int app_name=0x7f030000;
        public static final int app_version=0x7f030001;
        public static final int ble_not_supported=0x7f030002;
        public static final int connected=0x7f030003;
        public static final int device_rssi=0x7f030004;
        public static final int disconnected=0x7f030005;
        public static final int error_bluetooth_not_supported=0x7f030006;
        public static final int label_data=0x7f030007;
        public static final int label_device_address=0x7f030008;
        public static final int label_state=0x7f030009;
        public static final int menu_connect=0x7f03000a;
        public static final int menu_disconnect=0x7f03000b;
        public static final int menu_scan=0x7f03000c;
        public static final int menu_stop=0x7f03000d;
        public static final int no_data=0x7f03000e;
        public static final int text_detail=0x7f03000f;
        public static final int title_devices=0x7f030010;
        public static final int unknown_characteristic=0x7f030011;
        public static final int unknown_device=0x7f030012;
        public static final int unknown_service=0x7f030013;
    }
    public static final class styleable {
        /** Attributes that can be used with a color_picker.
           <p>Includes the following attributes:</p>
           <table>
           <colgroup align="left" />
           <colgroup align="left" />
           <tr><th>Attribute</th><th>Description</th></tr>
           <tr><td><code>{@link #color_picker_center_color com.AmoRgbLight.bluetooth.le:center_color}</code></td><td></td></tr>
           <tr><td><code>{@link #color_picker_center_radius com.AmoRgbLight.bluetooth.le:center_radius}</code></td><td></td></tr>
           <tr><td><code>{@link #color_picker_circle_radius com.AmoRgbLight.bluetooth.le:circle_radius}</code></td><td></td></tr>
           </table>
           @see #color_picker_center_color
           @see #color_picker_center_radius
           @see #color_picker_circle_radius
         */
        public static final int[] color_picker = {
            0x7f010000, 0x7f010001, 0x7f010002
        };
        /**
          <p>This symbol is the offset where the {@link com.AmoRgbLight.bluetooth.le.R.attr#center_color}
          attribute's value can be found in the {@link #color_picker} array.


          <p>Must be a color value, in the form of "<code>#<i>rgb</i></code>", "<code>#<i>argb</i></code>",
"<code>#<i>rrggbb</i></code>", or "<code>#<i>aarrggbb</i></code>".
<p>This may also be a reference to a resource (in the form
"<code>@[<i>package</i>:]<i>type</i>:<i>name</i></code>") or
theme attribute (in the form
"<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>")
containing a value of this type.
          @attr name com.AmoRgbLight.bluetooth.le:center_color
        */
        public static final int color_picker_center_color = 2;
        /**
          <p>This symbol is the offset where the {@link com.AmoRgbLight.bluetooth.le.R.attr#center_radius}
          attribute's value can be found in the {@link #color_picker} array.


          <p>Must be a dimension value, which is a floating point number appended with a unit such as "<code>14.5sp</code>".
Available units are: px (pixels), dp (density-independent pixels), sp (scaled pixels based on preferred font size),
in (inches), mm (millimeters).
<p>This may also be a reference to a resource (in the form
"<code>@[<i>package</i>:]<i>type</i>:<i>name</i></code>") or
theme attribute (in the form
"<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>")
containing a value of this type.
          @attr name com.AmoRgbLight.bluetooth.le:center_radius
        */
        public static final int color_picker_center_radius = 1;
        /**
          <p>This symbol is the offset where the {@link com.AmoRgbLight.bluetooth.le.R.attr#circle_radius}
          attribute's value can be found in the {@link #color_picker} array.


          <p>Must be a dimension value, which is a floating point number appended with a unit such as "<code>14.5sp</code>".
Available units are: px (pixels), dp (density-independent pixels), sp (scaled pixels based on preferred font size),
in (inches), mm (millimeters).
<p>This may also be a reference to a resource (in the form
"<code>@[<i>package</i>:]<i>type</i>:<i>name</i></code>") or
theme attribute (in the form
"<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>")
containing a value of this type.
          @attr name com.AmoRgbLight.bluetooth.le:circle_radius
        */
        public static final int color_picker_circle_radius = 0;
    };
}
