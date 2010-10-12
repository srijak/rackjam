/*
  Small input ~2s. Large input ~5s.
*/
import scala.io.Source

def removeInterleaved(ls: List[String], ls_new:List[String]): List[String]={
  ls match {
    case a::b::c::ls_rest =>
      if (a == c) removeInterleaved(ls_rest, a::ls_new)
      else removeInterleaved(b::c::ls_rest, a::ls_new)
    case _ => List.concat(ls.reverse, ls_new).reverse
  }
}
def removeInterleaved(ls: List[String]): List[String]={
  val l = removeInterleaved(ls, List())
  if (l.length != ls.length) removeInterleaved(l)
  else l
}
def solve(ls: List[String], ops: Int): Int = {
  ls match {
    case(l::Nil) => ops + 2
    case (l::ls_r) =>
      if (l == ls.reverse.head) solve(ls_r.init, ops + 2)
      else solve(ls.init, ops + 2)
    case List() => -5000
  }
}
def solver(ls:List[String]): Int={
  val remrep = ls.head::((ls zip ls.tail).collect{case (a, b) if a != b => b })
  val remint = removeInterleaved(remrep)
  solve(remint, 0) + (remrep.length - remint.length)  + ls.length 
}

Source.fromFile(args(0)).getLines.toList.tail.zipWithIndex
  .foreach{case(e, i) => println( "Case #"+ (i +1) + ": " + solver(e.split("").toList.tail))}
