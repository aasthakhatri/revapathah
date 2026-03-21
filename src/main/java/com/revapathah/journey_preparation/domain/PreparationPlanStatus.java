package com.revapathah.journey_preparation.domain;

public enum PreparationPlanStatus {

    PLANNED {
        @Override
        public boolean canTransitionTo(PreparationPlanStatus next) {
            return next == IN_PROGRESS || next == CANCELLED;
        }
    },

    IN_PROGRESS {
        @Override
        public boolean canTransitionTo(PreparationPlanStatus next) {
            return next == COMPLETED || next == CANCELLED;
        }
    },

    COMPLETED {
        @Override
        public boolean canTransitionTo(PreparationPlanStatus next) {
            return false;
        }
    },

    CANCELLED {
        @Override
        public boolean canTransitionTo(PreparationPlanStatus next) {
            return false;
        }
    };

    public abstract boolean canTransitionTo(PreparationPlanStatus next);
}
