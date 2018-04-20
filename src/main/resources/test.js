.fun void main void
    .logic vrun 4 > 3
    .while vrun
        .inv $java.lang.Thread sleep 1000 void
        .inv $java.lang.System currentTimeMillis vtime void void
        .inv println vtime
    .end-while
.end-fun main