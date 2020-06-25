
<?php

if ($_SERVER['REQUEST_METHOD'] == "GET")
{
   
     $con = mysqli_connect('localhost','root','','db_singuji');
 
    $query = "SELECT tb_mapel.mata_pelajaran, tb_guru.nama_guru FROM tb_guru, tb_mapel WHERE tb_guru.id_mapel=tb_mapel.id_mapel"; //select table in database
    $sql = mysqli_query($con, $query);
    $arraydata = array();
    while ($data = mysqli_fetch_array($sql)) {
        $arraydata[] = $data;
    }
 
    echo json_encode($arraydata);

 }
?>