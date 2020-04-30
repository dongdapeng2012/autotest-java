# Autotest JAVA Edition User Manual

## Cmd & Params

* openbrowser
	* >	must be: openbrowser chrome

* openurl real_url

* checktitle expect_title_value

* checkattribute element_id attribute_name expected_element_attribute_value

* click element_id

* clear element_id

* input element_id input_value

* js js_code

* inputbox element_id
	* >	will popup a input box and interupt operation until click ok button, user input will be keyed into the element 

* addparam param_key param_value

* addparamwithid param_key element_id attribute_name

* inputwithparam element_id param_key

* comparewithid element_id_1 attribute_name_1 element_id_2 attribute_name_2

* comparewithparam element_id attribute_name param_key

* loading
	* >	will popup a msg box and interupt operation until click ok button

* wait num_seconds

* quitbrowser

* runscript test_script_path
			
			
## Tips
* CMD are exact match, so if need comment out, just add a simple char like '-' is  ok
* please do not loop script, like test.txt call test1.txt when test1.txt call test.txt too.