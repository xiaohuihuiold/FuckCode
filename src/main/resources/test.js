    .line 1
.fun main void void

    .line 2
    .mov v0,"HelloWorld"
    .inv-s println,v0

    .line 3
    .mov v1,v0
    .inv-s print,v1

    .line 4
    .fun-call test void

    .line 5
    .inv-s println,"返回main方法"

    .line 6
    .fun-call test void

    .line 7
    .inv-s println,"即将退出程序"

    .ret void
.end-fun main

    .line 9
.fun test void void

    .line 10
    .mov v1,"小灰灰"
    .inv-s println,v1

    .ret void
.end-fun test