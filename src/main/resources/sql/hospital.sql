SELECT hospital_name, road_name_address FROM `hospital-info`.nation_wide_hospitals
where road_name_address like "경기도 수원시%"
and hospital_name like "%피부과%"
;