package main

//import statements
import "fmt"

//strings
var str = "2"
var str1 = "hello" + " " + "world"

//multiline string, can be added with other strings
var str3 = `
hello 
world
` + "hello"


//numbers
var x int64 = 1
var i int = int(x) 
var u uint 
var i64 uint64 
var u64 uint64 
var i32 int32 
var u32 uint32 

var f32 float32 = 1.5
var f64 float64 = float64(f32)

//zero values
//0 for numbers
//false for bool
//"" empty string for string

//go does not allow unused varaibales

//main function, go searches for main

//_ underscore is a way to trick the complier into thinking that a complier has been used


func main(){

	//var x int64 = 1
	//var i int = int(x) 
	//fmt.Println(x)
	//fmt.Printf("%v %t %T \n", str,str,str)
	var myInts []int
	if myInts == nil{
		myInts = make([]int, 3)
	}
	for i := range myInts {
		myInts[i] = i
	}

	fmt.Println(myInts)

	var str string
	
	if true{
		str = "hello"
	} else{
		str = "goodbye"
	}

	fmt.Println(str)
}
