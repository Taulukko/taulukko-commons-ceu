package com.taulukko.ceu.data;

import com.taulukko.ceu.CEUException;


public interface Factory {

	public abstract Cluster getCluster() throws CEUException;

}