# -*- coding: utf-8 -*-

import numpy as np
import matplotlib.pyplot as plt

def _compute_mean_features(window):
    """
    Computes the mean x, y and z acceleration over the given window.
    """
    mean = np.mean(window, axis=0)
    return mean


def _compute_median_features(window):
    """
    Computes the mean x, y and z acceleration over the given window.
    """
    median = np.median(window, axis=0)
    return median


def _compute_std_features(window):
    return np.std(window,axis=0)



def extract_features(window):
    # print window
    x = []
    x = np.append(x, _compute_std_features(window))
    x = np.append(x,  _compute_mean_features(window))
    x = np.append(x,  _compute_median_features(window))

    return x
