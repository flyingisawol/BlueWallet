import { createNavigationContainerRef, NavigationAction, ParamListBase, StackActions } from '@react-navigation/native';

export const navigationRef = createNavigationContainerRef<ParamListBase>();

export function navigate(name: string, params?: ParamListBase) {
  if (navigationRef.isReady()) {
    navigationRef.current?.navigate(name, params);
  }
}

export function dispatch(action: NavigationAction) {
  if (navigationRef.isReady()) {
    navigationRef.current?.dispatch(action);
  }
}

export function reset() {
  if (navigationRef.isReady()) {
    navigationRef.current?.reset({
      index: 0,
      routes: [{ name: 'UnlockWithScreen' }],
    });
  }
}

export function popToTop() {
  if (navigationRef.isReady()) {
    navigationRef.current?.dispatch(StackActions.popToTop());
  }
}

export function pop() {
  if (navigationRef.isReady()) {
    navigationRef.current?.dispatch(StackActions.pop());
  }
}
