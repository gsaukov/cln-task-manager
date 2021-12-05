export interface CounterTask {
  id: string
  name: string
  x: number
  y: number
  status: string
  createdAt: Date
  updateAt: Date
}

export interface CounterTaskRequest {
  name: string
  x: number
  y: number
}

export interface CounterTaskExecution {
  id: number
  data: CounterTaskExecutionState
}

export interface CounterTaskExecutionState {
  id: string
  x: number
  y: number
  status: string
  running: boolean
}
