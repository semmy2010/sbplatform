select f.* from cgform_t_field f,cgform_t_head h
 where f.table_id = h.id and f.is_show='Y' 
 and h.table_name=:tableName order by f.order_num 
  