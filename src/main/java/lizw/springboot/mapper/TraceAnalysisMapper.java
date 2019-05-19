package lizw.springboot.mapper;


import org.apache.ibatis.annotations.Mapper;

import lizw.springboot.entity.TraceAnalysisEntity;

@Mapper
public interface TraceAnalysisMapper {
	
   public int traceAnalysisMapper(TraceAnalysisEntity traceAnalysisEntity);

}
