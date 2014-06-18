<h1> The Dabba Search Engine </h1>
<?
mysql_connect("localhost","root","simple_password");
mysql_select_db("search_engine");
function depth($url)
{
$count=1;
for($i=0;$i<strlen($url)-1;$i++)
{
if($url[$i]=='/')
$count++;
if($url[$i]=='=')
$count++;
}
return $count;
}
?>
<form action="index.php" method="post">
Query: <input type="text" name="query">
<input type="submit">
</form>
<br /><br /><br />
<?
if($_POST['query'])
{
$query=$_POST['query'];
$words=explode(" ",$query);
$array=array();
$getpostitions=array();
foreach($words as $word)
{
$result=mysql_query("select url,tf,pos from indexer where term='$word';");
while($row=mysql_fetch_array($result))
{
$url=$row['url'];
$pos=explode(" ",$row['pos']);
$getpositions[$url][$word]=$pos;
$tf=$row['tf'];
if($array[$url])
{
$array[$url]+=(1.0*$tf);
continue;
}
$array[$url]=(1.0*$tf);
}
}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
$n=count($words);
for($i=0;$i<$n-1;$i++)
{
foreach($getpositions as $url=>$arr)
{
$r=mysql_query("select mag from url_magnitudes where url='$url';");
$x=mysql_fetch_array($r);
$mag=$x['mag'];
if($getpositions[$url][$words[$i]] && $getpositions[$url][$words[$i+1]])
{
foreach($getpositions[$url][$words[$i]] as $position)
{
if(array_search($position+1,$getpositions[$url][$words[$i+1]]))
$array[$url]+=50;
}
}
$array[$url]/=$mag;
}
}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
foreach($words as $word)
{
$result=mysql_query("select * from titles where term='$word';");
while($row=mysql_fetch_array($result))
{
$url=$row['url'];
$pos=explode(" ",$row['pos']);
$getpositions[$url][$word]=$pos;
if($array[$url])
{
$array[$url]+=100;
continue;
}
$array[$url]=100;
}
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*foreach($array as $url=>$rel)
{
$array[$url]/=depth($url);
//$array[$url]/=$mag;
}*/
///////////////////////////////////////////////////////////////////////////////////////////////////
arsort($array);
$count=0;
foreach($array as $url=>$tf)
{
$count++;
?>
<a href="<?=$url?>">
<?
print $url."</a><br />";
//print $tf."<br />";
//if($count>=5)
//break;
}
}
?>

