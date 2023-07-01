package com.lframework.xingyun.sc.impl.stock.transfer;

import com.lframework.starter.web.impl.BaseMpServiceImpl;
import com.lframework.xingyun.sc.entity.ScTransferOrderDetailReceive;
import com.lframework.xingyun.sc.mappers.ScTransferOrderDetailReceiveMapper;
import com.lframework.xingyun.sc.service.stock.transfer.ScTransferOrderDetailReceiveService;
import org.springframework.stereotype.Service;

@Service
public class ScTransferOrderDetailReceiveServiceImpl
    extends BaseMpServiceImpl<ScTransferOrderDetailReceiveMapper, ScTransferOrderDetailReceive>
    implements ScTransferOrderDetailReceiveService {

}
