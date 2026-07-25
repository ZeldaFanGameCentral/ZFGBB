package com.zfgc.zfgbb.services.system;

import java.util.function.Supplier;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InstallPhaseTransactions {

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public <T> T call(Supplier<T> work) {
		return work.get();
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void run(Runnable work) {
		work.run();
	}
}
